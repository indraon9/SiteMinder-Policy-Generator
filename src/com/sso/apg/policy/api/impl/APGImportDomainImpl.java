package com.sso.apg.policy.api.impl;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.netegrity.sdk.apiutil.SmApiException;
import com.sso.apg.policy.api.intf.APGPolicyAPI;
import com.sso.apg.policy.api.util.APGUtil;
import com.sso.apg.policy.api.util.SMAPIResult;
import com.sso.apg.policy.api.util.SMAPIResultEnum;
import com.sso.apg.policy.api.util.SMAPIResultList;
import com.sso.apg.policy.bean.DomainBO;
import com.sso.apg.policy.util.SMConnectionUtil;
import com.sso.apg.policy.util.SMMigrateUtil;


public class APGImportDomainImpl implements APGPolicyAPI {

	private SMConnectionUtil connUtil = null;
	public APGImportDomainImpl() throws UnknownHostException, SmApiException{
		connUtil = new SMConnectionUtil();
	}
	
	@Override
	public SMAPIResultList processAll(DomainBO domainBO) throws SmApiException, IOException {
		// TODO Auto-generated method stub
		String localFolderPath = domainBO.getImportFile().substring(0, domainBO.getImportFile().lastIndexOf(File.separator) + 1);
		String localFileName = domainBO.getImportFile().substring(domainBO.getImportFile().lastIndexOf(File.separator) + 1);
		List<String> unzippedFile = APGUtil.unzip(localFolderPath, localFileName);
		for(int i = 0 ; i<unzippedFile.size() ; i++){
			if(unzippedFile.get(i).endsWith(".smdif")){
				domainBO.setImportFile(unzippedFile.get(i));
				/*logger.debug(unzippedFile.get(i));*/
			}
			else if(unzippedFile.get(i).endsWith(".txt") && unzippedFile.get(i).contains("nameOidMap")){
				APGUtil.updateNameOidMap(unzippedFile.get(i), domainBO.getNotFoundObjects());
			}
		}
		SMMigrateUtil migrateUtil = new SMMigrateUtil();
		SMAPIResult result = new SMAPIResult();
		SMAPIResultList resultList = new SMAPIResultList();
		resultList.setSuccess(result.isSuccess());
		resultList.setTask("importTask");
		if(domainBO.isCustomExport()){
			result = migrateUtil.customImport(connUtil, domainBO);
			if(!result.isSuccess()){
				if(result.getReason() == SMAPIResultEnum.importTask_object_not_found.getReasonCode()){
					String [] messages = result.getMessages().split("\r\n");
					String []map = new String[3];
					for(int i = 0 ; i<messages.length ; i++){
						map = messages[i].split("=");
						if(map[0].equals("objectclass: Agent")){	
							SMAPIResult localResult = new SMAPIResult();
							localResult.setSuccess(false);
							localResult.setOperation("getAgent");
							localResult.setMessages(map[1]);
							localResult.setReason(SMAPIResultEnum.agent_object_not_found.getReasonCode());
							resultList.add(localResult);
						}else if(map[0].equals("objectclass: UserDirectory")){
							SMAPIResult localResult = new SMAPIResult();
							localResult.setSuccess(false);
							localResult.setOperation("getUserDirectory");
							localResult.setMessages(map[1]);
							localResult.setReason(SMAPIResultEnum.userDir_object_not_found.getReasonCode());
							resultList.add(localResult);
						}else if(map[0].equals("objectclass: AgentType")){
							SMAPIResult localResult = new SMAPIResult();
							localResult.setSuccess(false);
							localResult.setOperation("getAgentType");
							localResult.setMessages(map[1]);
							localResult.setReason(SMAPIResultEnum.agentType_object_not_found.getReasonCode());
							resultList.add(localResult);
						}else if(map[0].equals("objectclass: AgentTypeAttr")){
							SMAPIResult localResult = new SMAPIResult();
							localResult.setSuccess(false);
							localResult.setOperation("getAgentTypeAttr");
							localResult.setMessages(map[1]);
							localResult.setReason(SMAPIResultEnum.agentTypeAttr_object_not_found.getReasonCode());
							resultList.add(localResult);
						}else if(map[0].equals("objectclass: Scheme")){
							SMAPIResult localResult = new SMAPIResult();
							localResult.setSuccess(false);
							localResult.setOperation("getAuthScheme");
							localResult.setMessages(map[1]);
							localResult.setReason(SMAPIResultEnum.authScheme_object_not_found.getReasonCode());
							resultList.add(localResult);
						}
					}
				}else
					resultList.add(result);
			}
		}
		else
			migrateUtil.defaultImport(connUtil, domainBO);
		connUtil.logout();
		return resultList;
	}


}
