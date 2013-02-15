package com.founder.fix.fixflow.core.impl.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.founder.fix.fixflow.core.impl.Page;
import com.founder.fix.fixflow.core.impl.ProcessDefinitionQueryImpl;
import com.founder.fix.fixflow.core.impl.cache.CacheObject;
import com.founder.fix.fixflow.core.impl.model.DeploymentQueryImpl;
import com.founder.fix.fixflow.core.impl.runtime.ProcessInstanceQueryImpl;
import com.founder.fix.fixflow.core.impl.task.TaskQueryImpl;

public class DbSqlSession {

	protected CacheObject cacheObject;

	protected PersistentSession persistentSession;

	public DbSqlSession(Connection connection, CacheObject cacheObject) {
		this.cacheObject = cacheObject;
		persistentSession = new PersistentSession(connection);
	}

	// insert

	public void insert(String insertStatement, PersistentObject persistentObject) {

		persistentSession.insert(insertStatement, persistentObject);

	}

	// delete

	public void delete(String deleteStatement, String persistentObjectId) {

		persistentSession.delete(deleteStatement,persistentObjectId);
	}
	
	public void delete(String deleteStatement, PersistentObject persistentObject) {

		persistentSession.delete(deleteStatement,persistentObject);
	}

	// insert update

	public void save(String saveStatement, PersistentObject persistentObject) {

		persistentSession.save(saveStatement, persistentObject);

		/*
		if (cacheObject != null) {
			if (saveStatement.equals("saveProcessInstance")) {
				ProcessInstanceImpl processInstanceImpl = (ProcessInstanceImpl) persistentObject;
				cacheObject.putTransientCachedObject("ProcessInstance_" + processInstanceImpl.getId(), processInstanceImpl);
			}
		}
		*/

	}
	
	//  update
	public void update(String updateStatement, PersistentObject persistentObject)
	{
		persistentSession.update(updateStatement, persistentObject);
	}

	// select

	@SuppressWarnings("rawtypes")
	public List selectList(String statement) {
		return selectList(statement, null);
	}

	@SuppressWarnings({ "rawtypes" })
	public List selectList(String statement, Object parameter) {

		List loadedObjects = persistentSession.selectList(statement, parameter,null);
		return loadedObjects;
	}

	@SuppressWarnings({ "rawtypes" })
	public List selectList(String statement, Object parameter, Page page) {

		List loadedObjects;

			loadedObjects = persistentSession.selectList(statement, parameter, page);

		return loadedObjects;
	}

	public Object selectOne(String statement, Object parameter) {

		if (cacheObject != null) {
			if (statement.equals("selectProcessInstance")) {
				return selectProcessInstance(parameter);
			}
		}

		Object result = persistentSession.selectOne(statement, parameter);

		return result;
	}

	@SuppressWarnings("unchecked")
	private Object selectProcessInstance(Object parameter) {
		Map<String, Object> parameters = (HashMap<String, Object>) parameter;
		String processInstanceId = parameters.get("processInstanceId").toString();
		Object returnCachedObject = cacheObject.getCachedObject("ProcessInstance_" + processInstanceId);
		if (returnCachedObject != null) {
			return returnCachedObject;
		}

		Object result = persistentSession.selectOne("selectProcessInstance", parameter);
		if (result != null) {
			cacheObject.putTransientCachedObject("ProcessInstance_" + processInstanceId, result);
			return result;
		} else {
			return null;
		}

	}

	public DeploymentQueryImpl createDeploymentQuery() {
		return new DeploymentQueryImpl();
	}

	public ProcessDefinitionQueryImpl createProcessDefinitionQuery() {
		return new ProcessDefinitionQueryImpl();
	}

	public ProcessInstanceQueryImpl createProcessInstanceQuery() {
		return new ProcessInstanceQueryImpl();
	}

	public TaskQueryImpl createTaskQuery() {
		return new TaskQueryImpl();
	}

}
