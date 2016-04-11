package com.dy.surveypark.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dy.surveypark.dao.BaseDao;
import com.dy.surveypark.model.Right;
import com.dy.surveypark.service.RightService;
import com.dy.surveypark.util.DataUtil;
import com.dy.surveypark.util.StringUtil;
import com.dy.surveypark.util.ValidateUtil;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:13:34
 */
@Service("rightService")
public class RightServiceImpl extends BaseServiceImpl<Right> implements
		RightService {

	@Resource(name = "rightDao")
	public void setDao(BaseDao<Right> dao) {
		super.setDao(dao);
	}

	/**
	 * 保存或者更新权限
	 */
	public void saveOrUpdateRight(Right right) {
		// 插入操作
		int pos = 0;
		long code = 1L;
		if (right.getId() == null) {
			/*
			 * String hql =
			 * "from Right r order by r.rightPos desc , r.rightCode desc";
			 * List<Right> rights = this.findEntityByHQL(hql);
			 * if(!ValidateUtil.isValid(rights)){ pos = 0; code = 1L; }else{
			 * //得到最上面的right Right top = rights.get(0); int topPos =
			 * top.getRightPos(); long topCode = top.getRightCode(); //判断是否打到最大值
			 * if(topCode >= (1L << 60)){ pos = topPos + 1; code = 1L; }else{
			 * pos = topPos; code = topCode << 1; }
			 */
			// 方式2：性能更高
			String hql = "select max(r.rightPos),max(r.rightCode) from Right r where "
					+ "r.rightPos = (select max(rr.rightPos) from Right rr)";
			Object[] arr = (Object[]) this.uniqueResult(hql);
			// 必须使用包装类，因为有可能是0，返回的是null，只有包装类能接收null.
			Integer topPos = (Integer) arr[0];
			Long topCode = (Long) arr[1];
			// 没有权限
			if (topCode == null) {
				pos = 0;
				code = 1L;
			} else {
				// 权限码是否达到最大值
				if (topCode >= (1L << 60)) {
					pos = topPos + 1;
					code = 1L;
				} else {
					pos = topPos;
					code = topCode << 1;
				}
			}

			right.setRightPos(pos);
			right.setRightCode(code);
		}
		this.saveOrUpdateEntity(right);
	}

	/**
	 * 按照URL 追加权限
	 */
	public void appendRightByUrl(String url) {
		String hql = "select count(*) from Right r where r.rightUrl = ?";
		Long count = (Long) this.uniqueResult(hql, url);
		if (count == 0) {
			Right r = new Right();
			r.setRightUrl(url);
			this.saveOrUpdateRight(r);
			;
		}
	}

	/**
	 * 批量更新权限
	 */
	public void batchUpdateRights(List<Right> allRights) {
		if (ValidateUtil.isValid(allRights)) {
			String hql = "update Right r set r.rightName = ?,r.common = ? where r.id = ?";
			for (Right r : allRights) {
				this.batchEntityByHQL(hql, r.getRightName(), r.isCommon(),
						r.getId());
			}
		}
	}

	/**
	 * 查询在指定范围内的权限
	 */
	@Override
	public List<Right> findRightsInRange(Integer[] ownRightIds) {
		if (ValidateUtil.isValid(ownRightIds)) {
			String hql = "from Right r where r.id in ("
					+ StringUtil.arr2Str(ownRightIds) + ")";
			return this.findEntityByHQL(hql);
		}
		return null;
	}

	/**
	 * 查询不在指定范围内的权限
	 */
	@Override
	public List<Right> findRightsNotInRange(Set<Right> rights) {
		if (!ValidateUtil.isValid(rights)) {
			return this.findAllEntities();
		} else {
			String hql = "from Right r where r.id not in ("
					+ DataUtil.extract(rights) + ")";
			return this.findEntityByHQL(hql);
		}
	}

	/**
	 * 抽取所有right 的id,形成字符串，逗号分割
	 */
	// private String extractRightIds(Set<Right> rights) {
	// String temp = "";
	// if (ValidateUtil.isValid(rights)) {
	// for (Right r : rights) {
	// temp = temp + r.getId() + ",";
	// }
	// return temp.substring(0, temp.length() - 1);
	// }
	// return temp;
	// }

	/**
	 * 查询最大权限位
	 */
	public int getMaxRightPos() {
		String hql = "select max(r.rightPos) from Right r";
		Integer pos = (Integer) this.uniqueResult(hql);
		return pos == null ? 0 : pos;
	}

}
