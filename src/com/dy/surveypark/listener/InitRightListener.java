package com.dy.surveypark.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.dy.surveypark.model.Right;
import com.dy.surveypark.service.RightService;

/**
 * 初始化权限监听器，在spring容器初始化完成后立即调用。
 * 
 * @author DY
 * @version 创建时间：2015年11月10日 下午3:34:18
 */
@SuppressWarnings("rawtypes")
@Component
public class InitRightListener implements ApplicationListener,
		ServletContextAware {

	@Resource
	private RightService rs;

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {

		// 上下文刷新时间
		if (arg0 instanceof ContextRefreshedEvent) {
			// 查出所有权限
			List<Right> rights = rs.findAllEntities();
			Map<String, Right> map = new HashMap<String, Right>();
			for (Right r : rights) {
				map.put(r.getRightUrl(), r);
			}
			if (sc != null) {
				sc.setAttribute("all_rights_map", map);
				System.out.println("初始化所有权限到application 中！！");
			}

		}
	}

	/**
	 * 实现Spring 提供的ServletContextAware 接口，获取application 对象。（注意：不是struts提供的）
	 */
	// 接收ServletContext 对象
	private ServletContext sc;

	// 注入ServletContext 对象
	@Override
	public void setServletContext(ServletContext arg0) {
		this.sc = arg0;
	}

}
