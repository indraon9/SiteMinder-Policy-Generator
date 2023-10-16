package com.sso.apg.web.bean;

import org.apache.commons.validator.ValidatorAction;

public class ImportObject extends ValidatorAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9200128119925615302L;
	private String objectType;
	private String oldObjName;
	private String updatedObjName;
	
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getOldObjName() {
		return oldObjName;
	}
	public void setOldObjName(String oldObjName) {
		this.oldObjName = oldObjName;
	}
	public String getUpdatedObjName() {
		return updatedObjName;
	}
	public void setUpdatedObjName(String updatedObjName) {
		this.updatedObjName = updatedObjName;
	}
}
