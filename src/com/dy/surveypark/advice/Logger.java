package com.dy.surveypark.advice;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.dy.surveypark.model.Log;
import com.dy.surveypark.model.User;
import com.dy.surveypark.service.LogService;
import com.dy.surveypark.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 日志记录仪
 * 
 * @author DY
 * @version 创建时间：2015年11月11日 下午8:39:19
 */
public class Logger {

	@Autowired
	private LogService logService;

	/**
	 * 记录
	 */
	public Object record(ProceedingJoinPoint pjp) {
		Log log = new Log();
		try {
			ActionContext ac = ActionContext.getContext();
			// 设置操作人
			if (ac != null) {
				Map<String, Object> session = ac.getSession();
				if (session != null) {
					User user = (User) session.get("user");
					if (user != null) {
						log.setOperator("" + user.getId() + ":"
								+ user.getEmail());
					}
				}
			}
			// 操作名称
			String mname = pjp.getSignature().getName();
			log.setOperName(mname);
			// 操作参数
			Object[] params = pjp.getArgs();
			log.setOperParams(StringUtil.arr2Str(params));
			// 调用目标对象的方法
			Object ret = pjp.proceed();
			// 设置操作结果
			log.setOperResult("success");
			// 设置结果消息
			if (ret != null) {
				log.setResultMsg(ret.toString());
			}
			return ret;
		} catch (Throwable e) {
			log.setOperResult("failure");
			log.setResultMsg(e.getMessage());
		} finally {
			logService.saveEntity(log);
		}
		return null;
	}

}
