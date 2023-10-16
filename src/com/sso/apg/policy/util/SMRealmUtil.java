package com.sso.apg.policy.util;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.sso.apg.policy.bean.RealmBO;

public class SMRealmUtil {

	public SmApiResult addRealm(SMConnectionUtil connUtil, RealmBO realmBO) throws SmApiException {
		// TODO Auto-generated method stub
		return connUtil.getConnection().addRealm(realmBO.getRealm());
	}

}
