package com.sso.apg.web.bean;

import org.apache.struts.validator.ValidatorForm;

public class ExportFormBO extends ValidatorForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3475983585616888320L;
	private String domainName;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
}
