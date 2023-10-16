package com.sso.apg.policy.bean;

import java.util.ArrayList;
import java.util.List;

import com.netegrity.sdk.policyapi.SmDomain;
import com.netegrity.sdk.policyapi.SmPolicy;

public class PolicyBO {

	private String policyName;
	private String policyDesc;
	private SmPolicy policy = new SmPolicy();
	private List <RuleBO> rules = new ArrayList<RuleBO>();
	private String ruleActions;
	public PolicyBO() {
		// TODO Auto-generated constructor stub
	}
	public PolicyBO(String policyName, String policyDesc, SmDomain domain, boolean enabled) {
		// TODO Auto-generated constructor stub
		this(policyName,policyDesc);
		policy.setName(policyName);
		policy.setDescription(policyDesc);
		policy.setDomain(domain.getOid());
		policy.setEnabled(enabled);
	}
	public PolicyBO(String policyName, String policyDesc) {
		// TODO Auto-generated constructor stub
		this.policyName = policyName;
		this.policyDesc = policyDesc;
		
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyDesc() {
		return policyDesc;
	}
	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}
	public SmPolicy getPolicy() {
		return policy;
	}
	public void setPolicy(SmPolicy policy) {
		this.policy = policy;
	}
	public List<RuleBO> getRules() {
		return rules;
	}
	public void addRule(RuleBO rule){
		this.rules.add(rule);
	}
	public void setRules(List<RuleBO> rules) {
		this.rules = rules;
	}
	public String getRuleActions() {
		return ruleActions;
	}
	public void setRuleActions(String ruleActions) {
		this.ruleActions = ruleActions;
	}
}
