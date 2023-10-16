package com.sso.apg.policy.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.netegrity.sdk.policyapi.SmAgent;
import com.netegrity.sdk.policyapi.SmDomain;
import com.netegrity.sdk.policyapi.SmRealm;
import com.netegrity.sdk.policyapi.SmScheme;

public class RealmBO {

	private static final Logger logger = Logger.getLogger(RealmBO.class);
	private SmRealm realm = new SmRealm();
	private String name;
	private String resourceFilter;
	private String agentName;
	private String authScheme;
	private List<RuleBO> ruleList = new ArrayList<RuleBO>();
	public RealmBO(String name, SmAgent agent, SmDomain domain, SmScheme authScheme, String resourceFilter) {
		this(name, agent, domain, authScheme, resourceFilter, false, false);
	}
	public RealmBO(String name, SmAgent agent, SmDomain domain, SmScheme authScheme, String resourceFilter, boolean authRules, boolean azARules) {
		// TODO Auto-generated constructor stub
		realm.setAgent(agent.getOid());
		realm.setAgentType(agent.getAgentType());
		realm.setDomain(domain.getOid());
		realm.setScheme(authScheme);
		realm.setProcessAuthEvents(authRules);
		realm.setProcessAzEvents(azARules);
		realm.setName(name);
		realm.setDescription(name + " description");
		realm.setResourceFilter(resourceFilter);
		
	}
	
	public RealmBO(String name, String resourceFilter, String agentName, String authsScheme) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.resourceFilter = resourceFilter;
		this.agentName = agentName;
		this.authScheme = authsScheme;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResourceFilter() {
		return resourceFilter;
	}
	public void setResourceFilter(String resourceFilter) {
		this.resourceFilter = resourceFilter;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAuthScheme() {
		return authScheme;
	}
	public void setAuthScheme(String authScheme) {
		this.authScheme = authScheme;
	}
	public SmRealm getRealm(){
		return realm;
	}
	public List<RuleBO> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<RuleBO> ruleList) {
		this.ruleList = ruleList;
	}
	public void addRuleToRealm(RuleBO ruleBO){
		this.ruleList.add(ruleBO);
	}
	
	public void showAll() {
		// TODO Auto-generated method stub
		logger.debug(realm.getDescription() + realm.getDomainName() + realm.getResourceFilter());
	}

}
