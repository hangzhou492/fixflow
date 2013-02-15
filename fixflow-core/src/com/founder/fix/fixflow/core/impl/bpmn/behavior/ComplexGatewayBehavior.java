package com.founder.fix.fixflow.core.impl.bpmn.behavior;

import java.util.logging.Logger;

import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.impl.ComplexGatewayImpl;

import com.founder.fix.fixflow.core.exception.FixFlowException;
import com.founder.fix.fixflow.core.impl.expression.ExpressionMgmt;
import com.founder.fix.fixflow.core.runtime.ExecutionContext;

public class ComplexGatewayBehavior extends ComplexGatewayImpl {
	
	
private static final Logger LOG = Logger.getLogger(ComplexGatewayImpl.class.getName());;
	
	public void execute(ExecutionContext executionContext) {
		
		if(this.getActivationCondition()!=null)
		{
			String scriptTextString=((FormalExpression)this.getActivationCondition()).getBody();
			Object returnData=ExpressionMgmt.execute(scriptTextString, executionContext);
			if(returnData!=null)
			{
				boolean isActivation=(Boolean)returnData;
				if(isActivation)
				{
					super.execute(executionContext);
				}
				else
				{
					throw new FixFlowException("名称为: "+this.name+" 的条件网关条件不满足!");
				}
			}
			else
			{
				throw new FixFlowException("名称为: "+this.name+" 的条件网关表达式解释出错!");
			}
		}
		else
		{
			LOG.info("ComplexGateway id: "+this.id+" 条件网关未配置条件!");
			super.execute(executionContext);
		}

	}

}
