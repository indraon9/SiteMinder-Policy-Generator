package com.sso.apg.policy.bean;

import java.util.ArrayList;
import java.util.List;

import com.netegrity.sdk.policyapi.SmResponse;
import com.netegrity.sdk.policyapi.SmResponseAttr;

public class ResponseBO {

	private SmResponse response = new SmResponse();
	private String name;
	private String description;
	private String agentType;
	private String responseType;
	private String value;
	private String ruleActions;
	private List<ResponseAttrBO> responseAttrList = new ArrayList<ResponseAttrBO>();
	private List<PolicyBO> policyList = new ArrayList<PolicyBO>();
	public final static String wa_agentType="Web Agent";
	public static final String cookieResponse = SmResponseAttr.ATTR_WA_HTTP_COOKIE_VARIABLE;
	public static final String headerResponse = SmResponseAttr.ATTR_WA_HTTP_HEADER_VARIABLE;
	public static final String onAcceptRedirect = SmResponseAttr.ATTR_WA_ONACCEPT_REDIRECT;
	public static final String onRejectRedirect = SmResponseAttr.ATTR_WA_ONREJECT_REDIRECT;
	
	public ResponseBO(String name, String description, DomainBO domain, AgentTypeBO agentType){
		this(name, description);
		response.setDomain(domain.getDomain().getOid());
		response.setAgentType(agentType.getAgentType().getOid());
	}
	
	public ResponseBO(String name, String description){
		this(name,description,"");
	}
	
	public ResponseBO(String name, String description,String agentType){
		this.setName(name);
		this.setDescription(description);
		this.setAgentType(agentType);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public SmResponse getResponse() {
		return response;
	}

	public void setResponse(SmResponse response) {
		this.response = response;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getRuleActions() {
		return ruleActions;
	}

	public void setRuleActions(String ruleActions) {
		this.ruleActions = ruleActions;
	}

	public List<PolicyBO> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<PolicyBO> policyList) {
		this.policyList = policyList;
	}
	
	public void linkPolicyToResponse(PolicyBO policy){
		policyList.add(policy);
	}

	public List<ResponseAttrBO> getResponseAttrList() {
		return responseAttrList;
	}

	public void setResponseAttrList(List<ResponseAttrBO> responseAttrList) {
		this.responseAttrList = responseAttrList;
	}
	public void addResponseAttr(ResponseAttrBO responseAttr){
		this.responseAttrList.add(responseAttr);
	}
}
