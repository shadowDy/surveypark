package com.dy.surveypark.dao.impl;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:07:16
 */
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dy.surveypark.dao.BaseDao;

/**
 * 抽象的dao实现,专门用于继承
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	// 注入sessionFactory
	@Autowired
	private SessionFactory sf;

	private Class<T> clazz;

	public BaseDaoImpl() {
		// 得到泛型话超类
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	public void saveEntity(T t) {
		sf.getCurrentSession().save(t);
	}

	public void saveOrUpdateEntity(T t) {
		sf.getCurrentSession().saveOrUpdate(t);
	}

	public void updateEntity(T t) {
		sf.getCurrentSession().update(t);
	}

	public void deleteEntity(T t) {
		sf.getCurrentSession().delete(t);
	}

	/**
	 * 按照HQL语句进行批量更新
	 */
	public void batchEntityByHQL(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		q.executeUpdate();
	}

	public T loadEntity(Integer id) {
		return (T) sf.getCurrentSession().load(clazz, id);
	}

	public T getEntity(Integer id) {
		return (T) sf.getCurrentSession().get(clazz, id);
	}

	public List<T> findEntityByHQL(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		return q.list();
	}

	// 单值检索，确保查询结果有且只有一个结果
	public Object uniqueResult(String hql, Object... objects) {
		Query q = sf.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			q.setParameter(i, objects[i]);
		}
		return q.uniqueResult();
	}

	// 执行原生的sql 语句
	public void executeSql(String sql, Object... objects) {
		SQLQuery sq = sf.getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			sq.setParameter(i, objects[i]);
		}
		sq.executeUpdate();
	}

	// 执行sql 的原生查询
	public List executeSqlQuery(Class clazz,String sql, Object... objects) {
		SQLQuery sq = sf.getCurrentSession().createSQLQuery(sql);
		//添加实体类
		if(clazz != null){
			sq.addEntity(clazz);
		}
		for (int i = 0; i < objects.length; i++) {
			sq.setParameter(i, objects[i]);
		}
		return sq.list();
	}
}
