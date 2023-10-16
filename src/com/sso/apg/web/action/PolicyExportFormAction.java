package com.sso.apg.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PolicyExportFormAction extends Action{
	
	private static final Logger logger = Logger.getLogger(PolicyExportFormAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		logger.debug("Loading export form...");
		
		return mapping.findForward("exportPolicyForm");
	}

}
