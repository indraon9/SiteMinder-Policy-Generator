package com.sso.apg.policy.bean;

import com.netegrity.sdk.policyapi.SmAgentType;

public class AgentTypeBO {

	private SmAgentType agentType = new SmAgentType();

	public SmAgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(SmAgentType agentType) {
		this.agentType = agentType;
	}
}
