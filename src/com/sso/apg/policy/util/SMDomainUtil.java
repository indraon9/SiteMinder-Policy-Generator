package com.sso.apg.policy.util;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmDomain;

public class SMDomainUtil {

	public SmApiResult getDomain(SMConnectionUtil connUtil, String domainName,
			SmDomain domain) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().getDomain(domainName, domain);
	}

	public SmApiResult createDomian(SMConnectionUtil connUtil, SmDomain domain) throws SmApiException {
		// TODO Auto-generated method stub
		
		return connUtil.getConnection().addDomain(domain);
	}

	public SmApiResult deleteDomain(SMConnectionUtil connUtil, String domainName) throws SmApiException {
		// TODO Auto-generated method stub
		
		return connUtil.getConnection().deleteDomain(domainName);
	}

	public SmApiResult addUserDirToDomain(SMConnectionUtil connUtil,
			String domainName, String userDirName) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().addUserDirToDomain(userDirName, domainName);
	}

}
