package com.sso.apg.policy.util;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmUserDirectory;

public class SMDirectoryUtil {

	public SmApiResult getDirectory(SMConnectionUtil connUtil, String dirName,
			SmUserDirectory localDir) throws SmApiException {
		// TODO Auto-generated method stub
		
		return connUtil.getConnection().getUserDirectory(dirName, localDir);
		
	}

}
