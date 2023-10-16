package com.sso.apg.policy.util;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmScheme;

public class SMSchemeUtil {

	public SmApiResult getScheme(SMConnectionUtil connUtil,String schemeName, SmScheme scheme) throws SmApiException{
		return connUtil.getConnection().getScheme(schemeName, scheme);
	}
}
