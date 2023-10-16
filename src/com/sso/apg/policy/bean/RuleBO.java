package com.sso.apg.policy.bean;

import com.netegrity.sdk.policyapi.SmObjId;
import com.netegrity.sdk.policyapi.SmRealm;
import com.netegrity.sdk.policyapi.SmRule;

public class RuleBO {

	public static final String postAccess = SmRule.ACTION_POST;
	public static final String getAccess = SmRule.ACTION_GET;
	public static final String authAccept = SmRule.ACTION_ON_AUTH_ACCEPT;
	public static final String authReject = SmRule.ACTION_ON_AUTH_REJECT;
	public static final String authAttempt = SmRule.ACTION_ON_AUTH_ATTEMPT;
	public static final String accessAccept = SmRule.ACTION_ON_AZ_ACCEPT;
	public static final String accessReject = SmRule.ACTION_ON_AZ_REJECT;
	private SmRule rule = new SmRule();
	private String ruleName;
	private String ruleDesc;
	private boolean allowAccess;
	private boolean enabled;
	private String action;
	private ResponseBO response;
	private ResponseGroupBO responseGroup;
	
	public RuleBO(){
		
	}
	public RuleBO(String ruleName, String ruleDesc, boolean allowAccess, boolean enabled, String action){
		this.ruleName = ruleName;
		this.ruleDesc = ruleDesc;
		this.allowAccess = allowAccess;
		this.enabled = enabled;
		this.action = action;
	}
	
	public RuleBO(SmObjId domainOID, String ruleName, String ruleDesc, SmRealm smRealm, boolean allowAccess, boolean enabled, String action){
		
		rule.setDomain(domainOID);
		rule.setName(ruleName);
		rule.setDescription(ruleDesc);
		//rule.setAgent(agentOID);
		rule.setRealm(smRealm.getOid());
		rule.setResource("*");
		rule.setRegularExpression(false);
		rule.setAllowAccess(allowAccess);
		rule.setEnabled(enabled);
		rule.setAction(action);	
		
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public boolean isAllowAccess() {
		return allowAccess;
	}

	public void setAllowAccess(boolean allowAccess) {
		this.allowAccess = allowAccess;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public SmRule getRule(){
		return rule;
	}
	
	public void setRule(SmRule rule){
		this.rule = rule;
	}

	public ResponseBO getResponse() {
		return response;
	}

	public void setResponse(ResponseBO response) {
		this.response = response;
	}

	public ResponseGroupBO getResponseGroup() {
		return responseGroup;
	}

	public void setResponseGroup(ResponseGroupBO responseGroup) {
		this.responseGroup = responseGroup;
	}
}
