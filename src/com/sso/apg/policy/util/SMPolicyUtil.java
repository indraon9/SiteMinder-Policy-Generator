package com.sso.apg.policy.util;

import org.apache.log4j.Logger;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmDomain;
import com.netegrity.sdk.policyapi.SmPolicyLink;
import com.netegrity.sdk.policyapi.SmUserDirectory;
import com.netegrity.sdk.policyapi.SmUserPolicy;
import com.sso.apg.policy.bean.DomainBO;
import com.sso.apg.policy.bean.PolicyBO;
import com.sso.apg.policy.bean.RuleBO;

public class SMPolicyUtil {

	private static final Logger logger = Logger.getLogger(SMPolicyUtil.class);
public SmApiResult createPolicy(SMConnectionUtil connUtil, PolicyBO policyBO) throws SmApiException{
	
	return connUtil.getConnection().addPolicy(policyBO.getPolicy());
}

public SmApiResult createPolicyLink(SMConnectionUtil connUtil, SmDomain domain, PolicyBO policyBO, RuleBO ruleBO) throws SmApiException{
	SmPolicyLink policyLink = new SmPolicyLink();
	policyLink.setDomain(domain.getOid());
	policyLink.setPolicy(policyBO.getPolicy().getOid());
	policyLink.setRule(ruleBO.getRule().getOid());
	if(ruleBO.getResponse() != null)
		policyLink.setResponse(ruleBO.getResponse().getResponse().getOid());
	return connUtil.getConnection().addPolicyLink(policyLink);
}

public SmApiResult addUserDirToPolicy(SMConnectionUtil connUtil,
		DomainBO domainBO, PolicyBO policyBO) throws SmApiException {
	// TODO Auto-generated method stub
	SmApiResult smApiResult = new SmApiResult();
	for(int i = 0; i<domainBO.getUserDirectory().length ; i++){
	SmUserDirectory userDirectory = new SmUserDirectory();
	smApiResult = connUtil.getConnection().getUserDirectory(domainBO.getUserDirectory()[i], userDirectory);
	logger.debug("User directory " + domainBO.getUserDirectory()[i] + " found ? " + smApiResult.isSuccess());
	SmUserPolicy userPolicy = new SmUserPolicy();
	userPolicy.setDomain(domainBO.getDomain().getOid());
	userPolicy.setPolicy(policyBO.getPolicy().getOid());
	userPolicy.setFilterClass("organizationalunit");
	userPolicy.setFilterPath("ou=People, o=iam");
	userPolicy.setUserDirectory(userDirectory.getOid());
	smApiResult = connUtil.getConnection().addUserPolicy(userPolicy);
	logger.debug("User directory " + domainBO.getUserDirectory()[i] + " added to policy ? " + smApiResult.isSuccess());
	}
	return smApiResult;
}

}
