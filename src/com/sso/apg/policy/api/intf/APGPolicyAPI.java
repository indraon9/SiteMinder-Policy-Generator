package com.sso.apg.policy.api.intf;

import java.io.IOException;

import com.netegrity.sdk.apiutil.SmApiException;
import com.sso.apg.policy.api.util.SMAPIResultList;
import com.sso.apg.policy.bean.DomainBO;

public interface APGPolicyAPI {

	public SMAPIResultList processAll(DomainBO domainBO) throws SmApiException, IOException;
}
