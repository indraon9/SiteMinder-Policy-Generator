package com.sso.apg.policy.util;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmImportAttr;

public class SMTestTool {
	private static final Logger logger = Logger.getLogger(SMTestTool.class);
public static void main(String args[]){
	
	
	SMConnectionUtil connUtil;
	try {
		connUtil = new SMConnectionUtil();
	
	SmImportAttr importAttr = new SmImportAttr();
	importAttr.setFileName("E:\\JAVA STUFFS\\Auto Policy Generator\\output.smdif");
	importAttr.setOverWriteExistingDatabaseRecords();
	SmApiResult smApiResult = connUtil.getConnection().doImport(importAttr);
	logger.debug("Import successful ? " + smApiResult);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SmApiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}
