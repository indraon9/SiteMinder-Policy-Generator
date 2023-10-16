package com.sso.apg.policy.api.util;

public enum SMAPIResultEnum {

	importTask_object_not_found (1,"Some of the domain objects are not found"),
	agent_object_not_found (2,"Siteminder agent is not found"),
	agentType_object_not_found (3,"Siteminder agent type is not found"),
	agentTypeAttr_object_not_found (4,"Siteminder agent type attribute is not found"),
	userDir_object_not_found (5,"Siteminder user directory is not found"),
	authScheme_object_not_found (6,"Siteminder authentication scheme is not found"),
	importTask_domain_already_exists (7,"Domain already exists"),
	importTask_successful (8, "Import is successul");
	
	private int reasonCode;
	private String reason;
	
	SMAPIResultEnum(int reasonCode, String reason) {
		// TODO Auto-generated constructor stub
		this.reasonCode = reasonCode;
		this.reason = reason;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	public String getReason() {
		return reason;
	}

}
