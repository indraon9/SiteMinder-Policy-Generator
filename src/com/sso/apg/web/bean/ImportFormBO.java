package com.sso.apg.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class ImportFormBO extends ValidatorForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3475983585616888320L;
	private FormFile importFile;
	private List<ImportObject> notFoundObjects = new ArrayList<ImportObject>();
	private static final Logger logger = Logger.getLogger(ImportFormBO.class);
	
	public ImportFormBO(){
		logger.debug("Initializing ImportFormBO");
		notFoundObjects = new ArrayList<ImportObject>();
	}
	public FormFile getImportFile() {
		return importFile;
	}
	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	public List<ImportObject> getNotFoundObjects() {
		logger.debug("1# notFoundObjects : " + this.notFoundObjects);
		return this.notFoundObjects;
	}
	
	public ImportObject getObject(int index) {
		logger.debug("2# notFoundObjects : " + this.notFoundObjects);
		return this.notFoundObjects.get(index);
	}
	
	public void setObject(int index, ImportObject localObj) {
		this.notFoundObjects.add(localObj);
		logger.debug("3# notFoundObjects : " + this.notFoundObjects);
	}
	public void setNotFoundObjects(List<ImportObject> notFoundObjects) {
		this.notFoundObjects = notFoundObjects;
		logger.debug("4# notFoundObjects : " + this.notFoundObjects);
	}
	public void addObjects(ImportObject object){
		this.notFoundObjects.add(object);
		logger.debug("5# notFoundObjects : " + this.notFoundObjects.get(0));
	}	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		logger.debug("Reseting ImportFormBO");
		//notFoundObjects = new ArrayList<ImportObject>();
		if(request.getRequestURI().contains("importPolicyForm")){
			notFoundObjects = new ArrayList<ImportObject>();
		}
		importFile = null;
	}
	
	public void resetAll(ActionMapping mapping, HttpServletRequest request) {
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>Reset All ImportFormBO");
		notFoundObjects = new ArrayList<ImportObject>();
		importFile = null;
	}
}
