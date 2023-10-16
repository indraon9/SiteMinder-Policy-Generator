package com.sso.apg.policy.api.impl;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.netegrity.sdk.apiutil.SmApiException;
import com.sso.apg.policy.api.intf.APGPolicyAPI;
import com.sso.apg.policy.api.util.APGUtil;
import com.sso.apg.policy.api.util.SMAPIResultList;
import com.sso.apg.policy.bean.DomainBO;
import com.sso.apg.policy.util.SMConnectionUtil;
import com.sso.apg.policy.util.SMMigrateUtil;


public class APGExportDomainImpl implements APGPolicyAPI {

	private static final Logger logger = Logger.getLogger(APGExportDomainImpl.class);
	private SMConnectionUtil connUtil = null;
	public APGExportDomainImpl() throws UnknownHostException, SmApiException{
		connUtil = new SMConnectionUtil();
	}
	
	@Override
	public SMAPIResultList processAll(DomainBO domainBO) throws SmApiException, IOException {
		// TODO Auto-generated method stub
		SMMigrateUtil migrateUtil = new SMMigrateUtil();
		if(domainBO.isCustomExport()){
			migrateUtil.customExport(connUtil, domainBO);
			String [] file = new String[3];
			file[0] = domainBO.getExportFile();
			file[1] = domainBO.getExportFile().replace(".smdif", ".cfg");
			file[2] = domainBO.getExportFile().substring(0, domainBO.getExportFile().lastIndexOf(File.separatorChar) + 1) + "nameOidMap.txt";
			boolean isZipped = APGUtil.makeZip(file, domainBO.getExportFile().lastIndexOf(File.separatorChar + 1)  + "temp.zip");
			logger.debug("Zipped ? " + isZipped);
			domainBO.setExportZipFile(domainBO.getExportFile().lastIndexOf(File.separatorChar + 1) + "temp.zip");
			APGUtil.deleteFile(file[0]);
			APGUtil.deleteFile(file[1]);
			APGUtil.deleteFile(file[2]);
		}
		else
			migrateUtil.defaultExport(connUtil, domainBO);
		connUtil.logout();
		return null;
	}


}
