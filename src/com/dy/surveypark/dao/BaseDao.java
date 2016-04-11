package com.dy.surveypark.dao;



import java.util.List;

/**
 * BaseDao接口
 */
public interface BaseDao<T> {
	//写操作
	public void saveEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects);
	//执行原生的sql 语句
	public void executeSql(String sql,Object ...objects);
	//执行sql 的原生查询(可以指定是否封装成实体，判断clazz 是否为空)
	public List executeSqlQuery(Class clazz,String sql,Object ...objects);
	
	//读操作
	public T loadEntity(Integer id);
	public T getEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//单值检索，确保查询结果有且只有一个结果
	public Object uniqueResult(String hql, Object...objects);

}
