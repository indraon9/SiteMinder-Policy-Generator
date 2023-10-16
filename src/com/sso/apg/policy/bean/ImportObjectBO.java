package com.sso.apg.policy.bean;


public class ImportObjectBO{

	private String objectType;
	private String oldObjName;
	private String updatedObjName;
	
	public ImportObjectBO(String objType, String oldObjName, String updObjName){
		this.objectType = objType;
		this.oldObjName = oldObjName;
		this.updatedObjName = updObjName;
	}
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
