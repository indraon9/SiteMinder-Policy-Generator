package com.sso.apg.policy.bean;

public class ResponseAttrBO {

	private String attrId;
	private String attrName;
	private String attrDesc;
	private String attrType;
	private String attrValue;
	
	public ResponseAttrBO(String attrId,String attrName, String attrDesc, String attrType, String attrValue){
		this.attrId = attrId;
		this.attrName = attrName;
		this.attrDesc = attrDesc;
		this.attrType = attrType;
		this.attrValue = attrValue;
	}
	
	public ResponseAttrBO(String attrName, String attrDesc, String attrType, String attrValue){
		this.attrName = attrName;
		this.attrDesc = attrDesc;
		this.attrType = attrType;
		this.attrValue = attrValue;
	}
	
	public ResponseAttrBO(String attrType, String attrValue){
		this.attrType = attrType;
		this.attrValue = attrValue;
	}
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrDesc() {
		return attrDesc;
	}
	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
}
