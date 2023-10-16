package com.sso.apg.policy.util;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmAgent;
import com.netegrity.sdk.policyapi.SmAgentType;
import com.netegrity.sdk.policyapi.SmAgentTypeAttr;
import com.sso.apg.policy.bean.AgentTypeBO;

public class SMAgentUtil {

	public SmApiResult getAgent(SMConnectionUtil connUtil, String agentName, SmAgent agent) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().getAgent(agentName, agent);	
	}

	public SmApiResult getAgentType(SMConnectionUtil connUtil, String agentType, AgentTypeBO agentTypeBO) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().getAgentType(agentType,agentTypeBO.getAgentType());
		
	}
	
	public SmApiResult getAgentType(SMConnectionUtil connUtil, String agentTypeName, SmAgentType agentType) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().getAgentType(agentTypeName,agentType);
		
	}

	public SmApiResult getAgentTypeAttr(SMConnectionUtil connUtil, String agentTypeAttrName, SmAgentTypeAttr agentTypeAttr) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().getAgentTypeAttr(agentTypeAttrName, agentTypeAttr);
	}
}
