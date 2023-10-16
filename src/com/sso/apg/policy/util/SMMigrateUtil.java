package com.sso.apg.policy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import org.apache.log4j.Logger;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmAgent;
import com.netegrity.sdk.policyapi.SmAgentType;
import com.netegrity.sdk.policyapi.SmAgentTypeAttr;
import com.netegrity.sdk.policyapi.SmExportAttr;
import com.netegrity.sdk.policyapi.SmImportAttr;
import com.netegrity.sdk.policyapi.SmScheme;
import com.netegrity.sdk.policyapi.SmUserDirectory;
import com.sso.apg.policy.api.util.APGUtil;
import com.sso.apg.policy.api.util.SMAPIResult;
import com.sso.apg.policy.api.util.SMAPIResultEnum;
import com.sso.apg.policy.bean.DomainBO;

public class SMMigrateUtil {

	private static final Logger logger = Logger.getLogger(SMMigrateUtil.class);
	public SmApiResult defaultExport(SMConnectionUtil connUtil, DomainBO domainBO) throws SmApiException{

		SmExportAttr exportAttr = new SmExportAttr();
		exportAttr.setDomain(domainBO.getDomainName(), false);
		exportAttr.setFileName(domainBO.getExportFile(), true);
		SmApiResult smApiResult = connUtil.getConnection().doExport(exportAttr);
		logger.debug("Export successful ? " + smApiResult.isSuccess());
		return smApiResult;
	}

	public SmApiResult customExport(SMConnectionUtil connUtil, DomainBO domainBO) throws SmApiException, IOException{

		BufferedReader localDBufferSmdif = null;
		BufferedReader localBBufferSmdif = null;
		BufferedReader localBBufferCfg = null;

		SmExportAttr exportAttr = new SmExportAttr();
		exportAttr.setDomain(domainBO.getDomainName(), true);
		String localFileName = domainBO.getExportFile().substring(0, domainBO.getExportFile().lastIndexOf(File.separator) + 1) + (new Date()).getTime() + ".smdif";
		logger.debug(localFileName);
		exportAttr.setFileName(localFileName, true);
		SmApiResult smApiResult = connUtil.getConnection().doExport(exportAttr);
		logger.debug("Detailed Export successful ? " + smApiResult.isSuccess());
		if(smApiResult.isSuccess()){
			localDBufferSmdif = new BufferedReader(new FileReader(new File(localFileName)));
		}
		exportAttr = new SmExportAttr();
		exportAttr.setDomain(domainBO.getDomainName(), false);
		exportAttr.setFileName(domainBO.getExportFile(), true);
		smApiResult = connUtil.getConnection().doExport(exportAttr);
		logger.debug("Brief Export successful ? " + smApiResult.isSuccess());
		if(smApiResult.isSuccess()){
			localBBufferSmdif = new BufferedReader(new FileReader(new File(domainBO.getExportFile())));
			localBBufferCfg = new BufferedReader(new FileReader(new File(domainBO.getExportFile().replace(".smdif", ".cfg"))));
		}
		String [] searchString = {"objectclass: Agent", "objectclass: UserDirectory", "objectclass: AgentType", "objectclass: AgentTypeAttr", "objectclass: Scheme" };
		String []localStr = replaceOidByName(localDBufferSmdif, localBBufferSmdif, localBBufferCfg, searchString);
		BufferedReader tempBfReader = new BufferedReader(new StringReader(localStr[0]));
		APGUtil.writeFile(tempBfReader, domainBO.getExportFile().replace(".smdif", ".cfg"));
		tempBfReader = new BufferedReader(new StringReader(localStr[1]));
		APGUtil.writeFile(tempBfReader, domainBO.getExportFile());
		tempBfReader = new BufferedReader(new StringReader(localStr[2]));
		APGUtil.writeFile(tempBfReader, domainBO.getExportFile().substring(0, domainBO.getExportFile().lastIndexOf(File.separator) + 1) + "nameOidMap.txt");
		localStr = null;
		tempBfReader.close();
		localBBufferCfg.close();
		localBBufferSmdif.close();
		localDBufferSmdif.close();
		APGUtil.deleteFile(localFileName);
		APGUtil.deleteFile(localFileName.replace(".smdif", ".cfg"));
		return smApiResult;
	}


	public SmApiResult defaultImport(SMConnectionUtil connUtil, DomainBO domainBO) throws SmApiException{
		SmImportAttr importAttr = new SmImportAttr();
		importAttr.setFileName(domainBO.getExportFile());
		//importAttr.setOverWriteExistingDatabaseRecords();
		SmApiResult smApiResult = connUtil.getConnection().doImport(importAttr);
		logger.debug("Import successful ? " + smApiResult);
		return smApiResult;
	}

	public SMAPIResult customImport(SMConnectionUtil connUtil, DomainBO domainBO) throws IOException, SmApiException{

		BufferedReader localBufferSmdif = new BufferedReader(new FileReader(new File(domainBO.getImportFile())));
		BufferedReader localBufferCfg = new BufferedReader(new FileReader(new File(domainBO.getImportFile().replace(".smdif", ".cfg"))));
		BufferedReader localNameOidMap = new BufferedReader(new FileReader(new File(domainBO.getImportFile().substring(0, domainBO.getImportFile().lastIndexOf(File.separator) + 1) + "nameOidMap.txt")));
		String [] localStr = replaceNameByOid(connUtil, localBufferSmdif, localBufferCfg, localNameOidMap);
		SMAPIResult result = new SMAPIResult();
		result.setOperation("customImport");
		if(localStr != null && localStr[2] != null && localStr[2] != ""){
			//logger.debug(localStr[2]);
			result.setMessages(localStr[2]);
			result.setSuccess(false);
			result.setReason(SMAPIResultEnum.importTask_object_not_found.getReasonCode());
		}else{
			BufferedReader tempBfReader = new BufferedReader(new StringReader(localStr[1]));
			APGUtil.writeFile(tempBfReader, domainBO.getImportFile().replace(".smdif", ".cfg"));
			tempBfReader = new BufferedReader(new StringReader(localStr[0]));
			APGUtil.writeFile(tempBfReader, domainBO.getImportFile());

			SmImportAttr importAttr = new SmImportAttr();
			importAttr.setFileName(domainBO.getImportFile());
			SmApiResult smApiResult = connUtil.getConnection().doImport(importAttr);
			logger.debug("Import successful ? " + smApiResult);
			if(smApiResult.isSuccess()){
				result.setResult(smApiResult);
				result.setMessages("Import successful");
				result.setSuccess(true);
			}else{
				result.setResult(smApiResult);
				result.setMessages("Import failed");
				result.setSuccess(false);
				if(smApiResult.getStatus() == 63){
					result.setReason(SMAPIResultEnum.importTask_domain_already_exists.getReasonCode());
					result.setMessages(SMAPIResultEnum.importTask_domain_already_exists.getReason());
				}
			}
		}
		localBufferCfg.close();
		localBufferSmdif.close();
		localNameOidMap.close();
		localStr = null;

		return result;
	}


	private String[] replaceNameByOid(SMConnectionUtil connUtil, BufferedReader localBufferSmdif,
			BufferedReader localBufferCfg, BufferedReader localNameOidMap) throws IOException, SmApiException {
		// TODO Auto-generated method stub
		String line = "";
		String localBSmdif = "";
		String localBCfg = "";

		String [] tempStr = new String[3];
		String errorMsgs = "";
		while((line = localBufferSmdif.readLine()) != null){
			localBSmdif += line + "\r\n";
		}
		//logger.debug(localBSmdif);
		while((line = localBufferCfg.readLine()) != null){
			localBCfg += line + "\r\n";
		}
		//logger.debug(localBCfg);
		String [] map = new String[3];
		SMAgentUtil agentUtil = new SMAgentUtil();
		SmAgent localAgent = new SmAgent();
		SmAgentType localAgentType = new SmAgentType();
		SmAgentTypeAttr localAgentTypeAttr = new SmAgentTypeAttr();

		SMDirectoryUtil dirUtil = new SMDirectoryUtil();
		SmUserDirectory localDir = new SmUserDirectory();

		SMSchemeUtil schemeUtil = new SMSchemeUtil();
		SmScheme localScheme = new SmScheme();

		while((line = localNameOidMap.readLine()) != null){
			map = line.split("=");
			//logger.debug(map[0] + "> " + map[1]);
			if(map[0].equals("objectclass: Agent")){	
				agentUtil.getAgent(connUtil, map[1], localAgent);
				logger.debug("OID of the object : " + localAgent.getOid().toString());
				logger.debug("Name of the object : " + localAgent.getName());
				if(localAgent.getOid() != null && !localAgent.getOid().toString().equals("00-")){
					map[2] = localAgent.getOid().toString();
				}else{
					map[2] = map[1];
					errorMsgs += line + "\r\n";
				}
			}else if(map[0].equals("objectclass: UserDirectory")){
				dirUtil.getDirectory(connUtil, map[1], localDir);
				logger.debug("OID of the object : " + localDir.getOid());
				if(localDir.getOid() != null && localDir.getOid().toString() != null){
					map[2] = localDir.getOid().toString();
				}else{
					map[2] = map[1];
					errorMsgs += line + "\r\n";
				}
			}else if(map[0].equals("objectclass: AgentType")){
				agentUtil.getAgentType(connUtil, map[1], localAgentType);
				logger.debug("OID of the object : " + localAgentType.getOid());
				if(localAgentType.getOid() != null){
					map[2] = localAgentType.getOid().toString();
				}else{
					map[2] = map[1];
					errorMsgs += line + "\r\n";
				}
			}else if(map[0].equals("objectclass: AgentTypeAttr")){
				agentUtil.getAgentTypeAttr(connUtil, map[1], localAgentTypeAttr);
				logger.debug("OID of the object : " + localAgentTypeAttr.getOid());
				if(localAgentTypeAttr.getOid() != null){
					map[2] = localAgentTypeAttr.getOid().toString();
				}else{
					map[2] = map[1];
					errorMsgs += line + "\r\n";
				}
			}else if(map[0].equals("objectclass: Scheme")){
				schemeUtil.getScheme(connUtil, map[1], localScheme);
				logger.debug("OID of the object : " + localScheme.getOid());
				if(localScheme.getOid() != null){
					map[2] = localScheme.getOid().toString();
				}else{
					map[2] = map[1];
					errorMsgs += line + "\r\n";
				}
			}

			localBSmdif = localBSmdif.replaceAll(map[1], map[2]);
			localBCfg = localBCfg.replaceAll(map[1], map[2]);
		}
		tempStr[0] = localBSmdif;
		tempStr[1] = localBCfg;
		tempStr[2] = errorMsgs;
		//logger.debug(tempStr[1]);
		return tempStr;
	}

	private String[] replaceOidByName(BufferedReader localDBufferSmdif,
			BufferedReader localBBufferSmdif, BufferedReader localBBufferCfg,
			String []searchString) throws IOException {
		// TODO Auto-generated method stub
		String line = "";
		String localBSmdif = "";
		String localBCfg = "";
		String nameOidMap = "";
		while((line = localBBufferSmdif.readLine()) != null){
			localBSmdif += line + "\r\n";
		}
		while((line = localBBufferCfg.readLine()) != null){
			localBCfg += line + "\r\n";
		}
		while((line = localDBufferSmdif.readLine()) != null){
			for(int i = 0 ; i<searchString.length ; i++){
				if(line.equals(searchString[i])){
					String OID = localDBufferSmdif.readLine().substring("Oid:".length()).trim();
					String name = localDBufferSmdif.readLine().substring("Name:".length()).trim();
					nameOidMap += searchString[i] + "=" + name + "=" + OID + "\r\n";
					localBSmdif = localBSmdif.replaceAll(OID, name);
					localBCfg = localBCfg.replaceAll(OID, name);
				}
			}
		}
		String [] tempStr = new String[3];
		tempStr[0] = localBCfg;
		tempStr[1] = localBSmdif;
		tempStr[2] = nameOidMap;
		return tempStr;
	}

}

