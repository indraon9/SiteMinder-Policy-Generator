package com.sso.apg.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sso.apg.web.bean.ImportFormBO;

public class PolicyImportFormAction extends Action{
	
	private static final Logger logger = Logger.getLogger(PolicyImportFormAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		logger.debug("Loading import form...");
		ImportFormBO localForm = (ImportFormBO)form;
		localForm.resetAll(mapping, request);
		return mapping.findForward("importPolicyForm");
	}
}
