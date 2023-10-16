package com.sso.apg.policy.api.impl;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmAgent;
import com.netegrity.sdk.policyapi.SmDomain;
import com.netegrity.sdk.policyapi.SmScheme;
import com.sso.apg.policy.api.intf.APGPolicyAPI;
import com.sso.apg.policy.api.util.SMAPIResultList;
import com.sso.apg.policy.bean.DomainBO;
import com.sso.apg.policy.bean.PolicyBO;
import com.sso.apg.policy.bean.RealmBO;
import com.sso.apg.policy.bean.RuleBO;
import com.sso.apg.policy.util.SMAgentUtil;
import com.sso.apg.policy.util.SMCommonUtil;
import com.sso.apg.policy.util.SMConnectionUtil;
import com.sso.apg.policy.util.SMDomainUtil;
import com.sso.apg.policy.util.SMPolicyUtil;
import com.sso.apg.policy.util.SMRealmUtil;
import com.sso.apg.policy.util.SMResponseUtil;
import com.sso.apg.policy.util.SMRuleUtil;
import com.sso.apg.policy.util.SMSchemeUtil;

public class APGCreateDomainImpl implements APGPolicyAPI {

	private static final Logger logger = Logger.getLogger(APGCreateDomainImpl.class);
	private SMConnectionUtil connUtil = null;
	public APGCreateDomainImpl() throws UnknownHostException, SmApiException{
		connUtil = new SMConnectionUtil();
	}
	public SMAPIResultList processAll(DomainBO domainBO) throws SmApiException{

		SMAPIResultList resultList = new SMAPIResultList();
		SmDomain localDomain = new SmDomain();
		localDomain.setName(domainBO.getDomainName());
		localDomain.setDescription(domainBO.getDomainDesc());
		localDomain.setOid(SMCommonUtil.generateOID());
		localDomain.setMode(domainBO.getApplyGlobalPolicy());
		domainBO.setDomain(localDomain);
		SMDomainUtil domainUtil = new SMDomainUtil();
		SmApiResult smApiResult = domainUtil.createDomian(connUtil, domainBO.getDomain());
		logger.debug("Domain created ? " + smApiResult.isSuccess());
		logger.debug("Domain OID ? " + domainBO.getDomain().getOid());
		//resultList.add(smApiResult);

		if(!smApiResult.isSuccess())
			logger.debug(smApiResult.getMessage());

		for (int i = 0; i<domainBO.getUserDirectory().length; i++){
			smApiResult = domainUtil.addUserDirToDomain(connUtil, domainBO.getDomain().getName(), domainBO.getUserDirectory()[i]);
			logger.debug("Domain : " + domainBO.getDomain().getName() + " Directory : " + domainBO.getUserDirectory()[i] + "| Added ? " + smApiResult.isSuccess());
		}
		for(int i = 0; i<domainBO.getRealm().length;i++){
			SmAgent agent = new SmAgent();
			SMAgentUtil agentUtil = new SMAgentUtil();
			smApiResult = agentUtil.getAgent(connUtil, domainBO.getRealm()[i].getAgentName(), agent);
			logger.debug("Agent " + domainBO.getRealm()[i].getAgentName() + " found ? " + smApiResult.isSuccess());

			SmScheme authScheme = new SmScheme();
			SMSchemeUtil schemeUtil = new SMSchemeUtil();
			smApiResult = schemeUtil.getScheme(connUtil, domainBO.getRealm()[i].getAuthScheme(), authScheme);
			logger.debug("Scheme " + domainBO.getRealm()[i].getAuthScheme() + " found ? " + smApiResult.isSuccess());

			RealmBO localRealmBO = new RealmBO(domainBO.getRealm()[i].getName(),agent,domainBO.getDomain(),authScheme,domainBO.getRealm()[i].getResourceFilter());
			localRealmBO.setRuleList(domainBO.getRealm()[i].getRuleList());
			SMRealmUtil realmUtil = new SMRealmUtil();
			smApiResult = realmUtil.addRealm(connUtil, localRealmBO);
			domainBO.getRealm()[i]=localRealmBO;
			logger.debug("Realm added ? " + smApiResult.isSuccess());
			logger.debug("Realm OID ? " + domainBO.getRealm()[i].getRealm().getOid());
			if(!smApiResult.isSuccess())
				logger.debug(smApiResult.getMessage());
			for(int j = 0 ; j<domainBO.getRealm()[i].getRuleList().size() ; j++){
				RuleBO tempRuleBO = new RuleBO(domainBO.getDomain().getOid(), domainBO.getRealm()[i].getRuleList().get(j).getRuleName(), domainBO.getRealm()[i].getRuleList().get(j).getRuleDesc(), domainBO.getRealm()[i].getRealm(), domainBO.getRealm()[i].getRuleList().get(j).isAllowAccess(), domainBO.getRealm()[i].getRuleList().get(j).isEnabled(), domainBO.getRealm()[i].getRuleList().get(j).getAction());
				tempRuleBO.setResponse(domainBO.getRealm()[i].getRuleList().get(j).getResponse());
				SMRuleUtil ruleUtil = new SMRuleUtil();
				smApiResult = ruleUtil.addRule(connUtil,tempRuleBO);
				domainBO.getRealm()[i].getRuleList().get(j).setRule(tempRuleBO.getRule());
				logger.debug("Rule " + domainBO.getRule()[j].getRuleName() + " added ? " + smApiResult.isSuccess());
			}		
		}
		
		for(int i= 0; i<domainBO.getResponseList().size();i++){
			SMResponseUtil responseUtil = new SMResponseUtil();
			smApiResult = responseUtil.createResponse(connUtil, domainBO.getDomain(), domainBO.getResponseList().get(i));
			logger.debug("Response " + domainBO.getResponseList().get(i).getName() + " added ? " + smApiResult.isSuccess());
		}
		//SmPolicy policy = new SmPolicy();
		
		SMPolicyUtil policyUtil = new SMPolicyUtil();
		for(int i =0; i<domainBO.getPolicy().length; i++){
			PolicyBO tempPolicyBO = new PolicyBO(domainBO.getPolicy()[i].getPolicyName(),domainBO.getPolicy()[i].getPolicyDesc(),domainBO.getDomain(),true);
			tempPolicyBO.setRules(domainBO.getPolicy()[i].getRules());
			smApiResult = policyUtil.createPolicy(connUtil, tempPolicyBO);
			domainBO.getPolicy()[i] = tempPolicyBO;
			logger.debug("Policy " + domainBO.getPolicy()[i].getPolicyName() + " created ? " + smApiResult.isSuccess());
			for(int j =0;j<tempPolicyBO.getRules().size(); j++){
				logger.debug("Rule OID : " + tempPolicyBO.getRules().get(j).getRule().getOid());
				logger.debug("Rule Name : " + tempPolicyBO.getRules().get(j).getRuleName());
				//logger.debug("Response OID : " + tempPolicyBO.getRules().get(j).getResponse().getResponse().getOid());
				smApiResult = policyUtil.createPolicyLink(connUtil, domainBO.getDomain(), domainBO.getPolicy()[i], tempPolicyBO.getRules().get(j));
				//for(int k=0; k <domainBO.getRealm().length ; k++){
					//RuleBO ruleBO = new RuleBO(domainBO.getDomain().getOid(),domainBO.getPolicy()[i].getRules().get(j).getRuleName(), domainBO.getPolicy()[i].getRules().get(j).getRuleDesc(), domainBO.getRealm()[k].getRealm(), true, true, domainBO.getPolicy()[i].getRules().get(j).getAction());
					//ruleBO.setResponse(domainBO.getPolicy()[i].getRules().get(j).getResponse());
					//logger.debug(ruleBO.getRule().getDomain().isNull());
					//SMRuleUtil ruleUtil = new SMRuleUtil();
					//smApiResult = ruleUtil.addRule(connUtil,ruleBO);
					//logger.debug("Rule " + domainBO.getRule()[i].getRuleName() + " added ? " + smApiResult.isSuccess());
					
					//logger.debug("Rule " + domainBO.getRule()[i].getRuleName() + " added to policy ? " + smApiResult.isSuccess());
				//}
			}
			smApiResult = policyUtil.addUserDirToPolicy(connUtil,domainBO,domainBO.getPolicy()[i]);
		}
		connUtil.logout();
		
		//policyLink.set

		return resultList;
	}
}
