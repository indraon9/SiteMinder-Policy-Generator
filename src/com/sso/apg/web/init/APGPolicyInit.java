package com.sso.apg.web.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import sun.security.provider.PolicyParser;

import com.sso.apg.policy.bean.PolicyBO;
import com.sso.apg.policy.bean.ResponseAttrBO;
import com.sso.apg.policy.bean.ResponseBO;
import com.sso.apg.policy.bean.RuleBO;
import com.sso.apg.web.bean.AppObjectBO;


public class APGPolicyInit extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3671333871080854110L;
	private static final String policiesProp = "apg.domain.policy";
	private static final String responsesProp = "apg.domain.response";
	private static final String responsesAttrProp = "apg.domain.responseAttr";
	private static final String propDelm = ".";
	
	private static HashMap<String,ResponseAttrBO> responseAttrMap = new HashMap<String, ResponseAttrBO>();
	private static HashMap<String,ResponseBO> responseMap = new HashMap<String, ResponseBO>();
	private static HashMap<String,PolicyBO> policyMap = new HashMap<String, PolicyBO>();
	public void init(ServletConfig config) throws ServletException {
		Reader propReader;
		try {
			propReader = new FileReader(new File(config.getServletContext().getRealPath("/") + config.getInitParameter("policy-properties-location")));
		
		Properties prop = new Properties();
		prop.load(propReader);
		String []policies = prop.getProperty(policiesProp).split(",");
		for(int i=0;i<policies.length;i++){
			String policyName = prop.getProperty(policiesProp+propDelm+policies[i]+".name");
			String policyDesc = prop.getProperty(policiesProp+propDelm+policies[i]+".description");
			PolicyBO localPolicyBO = new PolicyBO(policyName,policyDesc);
			String ruleActions = prop.getProperty(policiesProp+propDelm+policies[i]+".ruleActions");
			System.out.println(">>>>>>>>>>> " + ruleActions);
			localPolicyBO.setRuleActions(ruleActions);
			policyMap.put(policies[i], localPolicyBO);
		}
		
		AppObjectBO.putObject("policy", policyMap);
		String [] respAttrs = prop.getProperty(responsesAttrProp).split(",");
		for(int i=0;i<respAttrs.length;i++){
			String respAttrId = prop.getProperty(responsesAttrProp+propDelm+respAttrs[i]+".id");
			String respAttrName = prop.getProperty(responsesAttrProp+propDelm+respAttrs[i]+".name");
			String respAttrDesc = prop.getProperty(responsesAttrProp+propDelm+respAttrs[i]+".description");
			String respAttrType = prop.getProperty(responsesAttrProp+propDelm+respAttrs[i]+".type");
			String respAttrValue = prop.getProperty(responsesAttrProp+propDelm+respAttrs[i]+".value");
			ResponseAttrBO localRespAttrBO = new ResponseAttrBO(respAttrId,respAttrName,respAttrDesc,respAttrType,respAttrValue);
			responseAttrMap.put(respAttrs[i], localRespAttrBO);
		}
		
		String []responses = prop.getProperty(responsesProp).split(",");
		for(int i=0;i<responses.length;i++){
			String respName = prop.getProperty(responsesProp+propDelm+responses[i]+".name");
			String respDesc = prop.getProperty(responsesProp+propDelm+responses[i]+".description");
			ResponseBO localResponse = new ResponseBO(respName, respDesc);
			String [] respAttrList = prop.getProperty(responsesProp+propDelm+responses[i]+".responseAttrList").split(",");
			for(int j=0;j<respAttrList.length;j++){
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + respAttrList[j]);
				localResponse.addResponseAttr(responseAttrMap.get(respAttrList[j]));
			}
			String ruleActions = prop.getProperty(responsesProp+propDelm+responses[i]+".ruleAction");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>rule actions" + ruleActions);
			localResponse.setRuleActions(ruleActions);
			responseMap.put(respName, localResponse);
		}
		AppObjectBO.putObject("response", responseMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
