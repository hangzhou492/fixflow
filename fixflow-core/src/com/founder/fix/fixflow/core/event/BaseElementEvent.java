package com.founder.fix.fixflow.core.event;

import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import com.founder.fix.fixflow.core.impl.connector.ConnectorDefinition;


public interface BaseElementEvent {
	  public static final String EVENTTYPE_TRANSITION = "transition";
	  public static final String EVENTTYPE_BEFORE_SIGNAL = "before-signal";
	  public static final String EVENTTYPE_AFTER_SIGNAL = "after-signal";
	  public static final String EVENTTYPE_PROCESS_START = "process-start";
	  public static final String EVENTTYPE_PROCESS_END = "process-end";
	  public static final String EVENTTYPE_NODE_ENTER = "node-enter";
	  public static final String EVENTTYPE_NODE_LEAVE = "node-leave";
	  public static final String EVENTTYPE_TASK_ROLLBACK = "task-rollback";
	  public static final String EVENTTYPE_TASK_JUMP = "task-jump";
	  public static final String EVENTTYPE_SUPERSTATE_ENTER = "superstate-enter";
	  public static final String EVENTTYPE_SUPERSTATE_LEAVE = "superstate-leave";
	  public static final String EVENTTYPE_SUBPROCESS_CREATED = "subprocess-created";
	  public static final String EVENTTYPE_SUBPROCESS_END = "subprocess-end";
	  public static final String EVENTTYPE_TASK_CREATE = "task-create";
	  public static final String EVENTTYPE_TASK_ASSIGN = "task-assign";
	  public static final String EVENTTYPE_TASK_ASSIGN_CHANGE = "task-assign-change";
	  public static final String EVENTTYPE_TASK_START = "task-start";
	  public static final String EVENTTYPE_TASK_END = "task-end";
	  public static final String EVENTTYPE_TIMER = "timer";
	  
	  
	  
	  
	  
	  List<ConnectorDefinition> getConnectors();
	  
	  boolean hasConnectors();
	  
	  ConnectorDefinition addConnector(ConnectorDefinition connector);
	  
	  void removeConnector(ConnectorDefinition connector);
	  
	  String getEventType();
	  
	  BaseElement getBaseElement();
	  
	  void setBaseElement(BaseElement baseElement);
	  
	  String getId();
	  
	  void setId(String Id);

}
