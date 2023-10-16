package com.sso.apg.policy.util;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.sso.apg.policy.bean.RuleBO;

public class SMRuleUtil {

	public SmApiResult addRule(SMConnectionUtil connUtil, RuleBO ruleBO) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().addRule(ruleBO.getRule());
	}

}
