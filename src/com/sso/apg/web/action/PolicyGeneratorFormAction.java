package com.sso.apg.web.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PolicyGeneratorFormAction extends Action
{
	private static final Logger logger = Logger.getLogger(PolicyGeneratorFormAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		//Invalidating all session
		//request.getSession().invalidate();
		request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.ENGLISH);
		logger.debug("Loading Policy Form...");
		ActionForward forward = mapping.findForward("loadPolicyForm");
		return forward;
	}
	
}
