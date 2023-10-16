package com.sso.apg.policy.bean;

import java.util.ArrayList;
import java.util.List;

import com.netegrity.sdk.policyapi.SmDomain;

public class DomainBO {

	private SmDomain domain;
	private String domainName;
	private String domainDesc;
	private String[] userDirectory;
	private RealmBO[] realm;
	private PolicyBO[] policy;
	private RuleBO[] rule;
	private List<ResponseBO> responseList = new ArrayList<ResponseBO>();
	private int applyGlobalPolicy = 2;
	private boolean customExport = false;
	private String exportFile;
	private String exportZipFile;
	private String importFile;
	private List<ImportObjectBO> notFoundObjects = new ArrayList<ImportObjectBO>(); 
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
	public String[] getUserDirectory() {
		return userDirectory;
	}
	public void setUserDirectory(String[] userDirectory) {
		this.userDirectory = userDirectory;
	}
	public RealmBO[] getRealm() {
		return realm;
	}
	public void setRealm(RealmBO[] realm) {
		this.realm = realm;
	}
	public PolicyBO[] getPolicy() {
		return policy;
	}
	public void setPolicy(PolicyBO[] policy) {
		this.policy = policy;
	}
	public RuleBO[] getRule() {
		return rule;
	}
	public void setRule(RuleBO[] rule) {
		this.rule = rule;
	}
	public SmDomain getDomain() {
		return domain;
	}
	public void setDomain(SmDomain domain) {
		this.domain = domain;
	}
	public List<ResponseBO> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<ResponseBO> responseList) {
		this.responseList = responseList;
	}
	
	public void addResponseToDomain(ResponseBO response){
		responseList.add(response);
	}
	public int getApplyGlobalPolicy() {
		return applyGlobalPolicy;
	}
	public void setApplyGlobalPolicy(int applyGlobalPolicy) {
		this.applyGlobalPolicy = applyGlobalPolicy;
	}
	public boolean isCustomExport() {
		return customExport;
	}
	public void setCustomExport(boolean customExport) {
		this.customExport = customExport;
	}
	public String getExportFile() {
		return exportFile;
	}
	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}
	public String getExportZipFile() {
		return exportZipFile;
	}
	public void setExportZipFile(String exportZipFile) {
		this.exportZipFile = exportZipFile;
	}
	public String getImportFile() {
		return importFile;
	}
	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}
	public List<ImportObjectBO> getNotFoundObjects() {
		return notFoundObjects;
	}
	public void setNotFoundObjects(List<ImportObjectBO> notFoundObjects) {
		this.notFoundObjects = notFoundObjects;
	}
	
	public void addObjects(ImportObjectBO object){
		this.notFoundObjects.add(object);
	}
}
