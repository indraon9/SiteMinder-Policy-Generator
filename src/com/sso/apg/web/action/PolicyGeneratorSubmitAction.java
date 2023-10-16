package com.sso.apg.web.action;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netegrity.sdk.apiutil.SmApiException;
import com.sso.apg.policy.api.impl.APGCreateDomainImpl;
import com.sso.apg.policy.api.intf.APGPolicyAPI;
import com.sso.apg.policy.bean.DomainBO;
import com.sso.apg.policy.bean.PolicyBO;
import com.sso.apg.policy.bean.RealmBO;
import com.sso.apg.policy.bean.ResponseAttrBO;
import com.sso.apg.policy.bean.ResponseBO;
import com.sso.apg.policy.bean.RuleBO;
import com.sso.apg.web.bean.AppObjectBO;
import com.sso.apg.web.bean.PolicyFormBO;

public class PolicyGeneratorSubmitAction extends Action {

	private static final Logger logger = Logger.getLogger(PolicyGeneratorSubmitAction.class);
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		ActionForward forward = null;
		try {
			PolicyFormBO localForm = (PolicyFormBO)form;
			logger.debug("rule length " + localForm.getRuleList().length);
			DomainBO domainBO = new DomainBO();
			domainBO.setDomainName(localForm.getDomainName());
			domainBO.setDomainDesc(localForm.getDomainDesc());
			domainBO.setApplyGlobalPolicy(2);
			HashMap<String,PolicyBO>  policyMap = (HashMap<String, PolicyBO>) AppObjectBO.getObjectMap().get("policy");
			PolicyBO [] policy = new PolicyBO[policyMap.size()];
			Iterator<String> localIter = policyMap.keySet().iterator();
			int iterCount = 0;
			while(localIter.hasNext()){
				PolicyBO tempBO = (PolicyBO) policyMap.get(localIter.next());
				policy[iterCount] = new PolicyBO(tempBO.getPolicyName(),tempBO.getPolicyDesc());
				//policy[iterCount].setRules(tempBO.getRules());
				policy[iterCount].setRuleActions(tempBO.getRuleActions());
				iterCount++;
			}
			
			for(int i = 0; i<policyMap.size() ; i++){
				
			}
			/*
			 * Property file changes
			 * 
			 * policy[0] = new PolicyBO("Allow Access Policy", " Allow Access Policy");
			policy[1] = new PolicyBO("Auth Fail Policy", " Auth Fail Policy");*/
			RealmBO [] realmList = new RealmBO[localForm.getRealm().length];
			for(int i = 0 ; i<localForm.getRealm().length ; i++){
				RealmBO realm = new RealmBO(localForm.getRealmName()[i],localForm.getRealm()[i], localForm.getAgent()[i], localForm.getAuthScheme()[i]);
				realmList[i] = realm;
			}
			
			HashMap<String,ResponseBO> responseMap = (HashMap<String, ResponseBO>) AppObjectBO.getObjectMap().get(AppObjectBO.responseMap);
			ResponseBO [] responseList = new ResponseBO[responseMap.size()];
			Iterator<String> localIter1 = responseMap.keySet().iterator();
			int iterCount1 = 0;
			while(localIter1.hasNext()){
				ResponseBO tempRespBO = (ResponseBO) responseMap.get(localIter1.next());
				responseList[iterCount1] = new ResponseBO(tempRespBO.getName(), tempRespBO.getDescription(),ResponseBO.wa_agentType);
				responseList[iterCount1].setRuleActions(tempRespBO.getRuleActions());
				logger.debug("response action : " + tempRespBO.getRuleActions());
				//responseList[iterCount1].setResponseType(tempRespBO.getResponseType());
				logger.debug("Number of response attributes : " + tempRespBO.getResponseAttrList().size());
				
				for(int i = 0; i<tempRespBO.getResponseAttrList().size(); i++){
					logger.debug("Printing response attribute : " + tempRespBO.getResponseAttrList().get(i));
					String respAttrType = tempRespBO.getResponseAttrList().get(i).getAttrType();
					String respAttrValue = tempRespBO.getResponseAttrList().get(i).getAttrValue();
					ResponseAttrBO localRespAttrBO = new ResponseAttrBO(respAttrType, respAttrValue);
					responseList[iterCount1].addResponseAttr(localRespAttrBO);
				}
				domainBO.addResponseToDomain(responseList[iterCount1]);
				iterCount1++;
			}
			/*
			 * Property file changes
			 * 
			 * ResponseBO responseBO = new ResponseBO("Allow Access Response", "Allow access response",ResponseBO.wa_agentType);
			responseBO.setResponseType(ResponseBO.cookieResponse);
			responseBO.setValue("guidCookie=<% userattr=\"guid\" %>");
			//responseBO.linkPolicyToResponse(policy[0]);
			domainBO.addResponseToDomain(responseBO);
			ResponseBO responseBO1 = new ResponseBO("Auth Fail Response", "Auth Fail response",ResponseBO.wa_agentType);
			responseBO1.setResponseType(ResponseBO.cookieResponse);
			responseBO1.setValue("AuthFail=true");
			//responseBO1.linkPolicyToResponse(policy[1]);
			domainBO.addResponseToDomain(responseBO1);
*/
			RuleBO[] rules = new RuleBO[localForm.getRuleList().length];
			logger.debug("Realms : " + localForm.getRealm().length);
			for(int j = 0; j<localForm.getRealm().length ; j++){
				for(int i = 0; i<localForm.getRuleList().length; i++){
					String ruleName = localForm.getRealmName()[j]+ " " + localForm.getRuleList()[i] + " Rule";
					String ruleDesc = localForm.getRealmName()[j]+ " " + localForm.getRuleList()[i] + " Rule";
					//logger.debug("enabled size : "+ localForm.getEnabled());
					RuleBO rule = new RuleBO(ruleName,ruleDesc,true,localForm.getEnabled()[j],localForm.getRuleList()[i]);
					rules [i]= rule;
					logger.debug(rule.getRuleName() + "|" + rule.getAction());
					for(int k=0;k<policy.length;k++){
						if(policy[k].getRuleActions().contains(rule.getAction())){
							logger.debug("rule added to policy : " + policy[k].getPolicyName());
							policy[k].addRule(rule);
						}
					}
					//logger.debug("number of responses : " + responseList.length);
					for(int l = 0; l<responseList.length; l++){
						logger.debug("response action : " + responseList[l].getRuleActions());
						if(responseList[l].getRuleActions().contains(rule.getAction())){
							logger.debug("response added to rule : " + responseList[l].getName());
							rule.setResponse(responseList[l]);
						}
					}
					if(rule.getAction().equalsIgnoreCase("allowAccess")){
						rule.setAction(RuleBO.getAccess + "," + RuleBO.postAccess);
					}
					/*
					 * Property file changes
					 * 
					 * if(rule.getAction().equalsIgnoreCase(RuleBO.accessAccept) || rule.getAction().equalsIgnoreCase(RuleBO.authAccept) || rule.getAction().equalsIgnoreCase("allowAccess")){
						if(rule.getAction().equalsIgnoreCase("allowAccess")){
							rule.setAction(RuleBO.getAccess + "," + RuleBO.postAccess);
						}
						rule.setResponse(responseBO);
						policy[0].addRule(rule);
					}else if(rule.getAction().equalsIgnoreCase(RuleBO.accessReject)||rule.getAction().equalsIgnoreCase(RuleBO.authAttempt)||rule.getAction().equalsIgnoreCase(RuleBO.authReject)){
						rule.setResponse(responseBO1);
						policy[1].addRule(rule);
					}*/
					realmList[j].addRuleToRealm(rule);
				}
				

			}
			
			domainBO.setRealm(realmList);
			domainBO.setPolicy(policy);
			domainBO.setRule(rules);
			domainBO.setUserDirectory(localForm.getUserDir());

			forward = mapping.findForward("success");
			APGPolicyAPI policyApi = new APGCreateDomainImpl();
			policyApi.processAll(domainBO);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return forward;

	}

}
