package com.sso.apg.web.bean;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


public class PolicyFormBO extends ValidatorForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7906716270468439570L;
	private String domainName;
	private String domainDesc;
	private String[] realm;
	private String[] realmName;
	private String[] realmDesc;
	private String[] authScheme;
	private String[] ruleList;
	private String[] userDir;
	private String[] agent;
	private boolean[] allowAccess;
	private boolean[] enabled;
	


	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainDesc() {
		return domainDesc;
	}

	public void setDomainDesc(String domainDesc) {
		this.domainDesc = domainDesc;
	}

	public String[] getRealm() {
		return realm;
	}

	public void setRealm(String[] realm) {
		this.realm = realm;
	}

	public String[] getRealmName() {
		return realmName;
	}

	public void setRealmName(String[] realmName) {
		this.realmName = realmName;
	}

	public String[] getRealmDesc() {
		return realmDesc;
	}

	public void setRealmDesc(String[] realmDesc) {
		this.realmDesc = realmDesc;
	}

	public String[] getAuthScheme() {
		return authScheme;
	}

	public void setAuthScheme(String[] authScheme) {
		this.authScheme = authScheme;
	}

	public String[] getAgent() {
		return agent;
	}

	public void setAgent(String[] agent) {
		this.agent = agent;
	}

	public String[] getRuleList() {
		return ruleList;
	}

	public void setRuleList(String[] ruleList) {
		this.ruleList = ruleList;
	}


	public String[] getUserDir() {
		return userDir;
	}

	public void setUserDir(String[] userDir) {
		this.userDir = userDir;
	}

	public boolean[] getEnabled() {
		System.out.println("size enabled : " + enabled);
		return enabled;
	}

	public void setEnabled(boolean[] enabled) {
		this.enabled = enabled;
	}

	public boolean[] getAllowAccess() {
		return allowAccess;
	}

	public void setAllowAccess(boolean[] allowAccess) {
		this.allowAccess = allowAccess;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		System.out.println(">>>>>>reset policy form");
		ruleList = null;
		realm = null;
		realmName= null;
		realmDesc = null;
		agent = null;
		authScheme = null;
		userDir=null;
		enabled=null;
	}
}
