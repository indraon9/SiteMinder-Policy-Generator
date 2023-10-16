package com.sso.apg.policy.util;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmAgentType;
import com.netegrity.sdk.policyapi.SmDomain;
import com.netegrity.sdk.policyapi.SmResponseAttr;
import com.sso.apg.policy.bean.ResponseBO;

public class SMResponseUtil {

	public SmApiResult createResponse(SMConnectionUtil connUtil, SmDomain domain, ResponseBO responseBO) throws SmApiException{
		//SmResponse response = new SmResponse();
		SmAgentType agentType = new SmAgentType();
		SmApiResult smApiResult = connUtil.getConnection().getAgentType(responseBO.getAgentType(), agentType);
		responseBO.getResponse().setName(responseBO.getName());
		responseBO.getResponse().setDomain(domain.getOid());
		responseBO.getResponse().setAgentType(agentType.getOid());
		
		smApiResult = connUtil.getConnection().addResponse(responseBO.getResponse());
		for(int i = 0; i<responseBO.getResponseAttrList().size(); i++){
			SmResponseAttr responseAttr = new SmResponseAttr();
			responseAttr.setDomain(domain.getOid());
			responseAttr.setAgentTypeAttr(responseBO.getResponseAttrList().get(i).getAttrType());
			responseAttr.setResponse(responseBO.getResponse().getOid());
			responseAttr.setValue(responseBO.getResponseAttrList().get(i).getAttrValue());
			smApiResult = connUtil.getConnection().addResponseAttr(responseAttr);
		}
		/*
		 * Property file changes
		 * 
		 * SmResponseAttr responseAttr = new SmResponseAttr();
		responseAttr.setDomain(domain.getOid());
		responseAttr.setAgentTypeAttr(responseBO.getResponseType());
		responseAttr.setResponse(responseBO.getResponse().getOid());
		responseAttr.setValue(responseBO.getValue());
		smApiResult = connUtil.getConnection().addResponseAttr(responseAttr);*/
		
		
		return smApiResult;
	}
}
