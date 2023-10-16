package com.sso.apg.policy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.policyapi.SmAgent;
import com.netegrity.sdk.policyapi.SmAgentType;
import com.netegrity.sdk.policyapi.SmDomain;
import com.netegrity.sdk.policyapi.SmExportAttr;
import com.netegrity.sdk.policyapi.SmPolicy;
import com.netegrity.sdk.policyapi.SmPolicyLink;
import com.netegrity.sdk.policyapi.SmResponse;
import com.netegrity.sdk.policyapi.SmResponseAttr;
import com.netegrity.sdk.policyapi.SmRule;
import com.netegrity.sdk.policyapi.SmScheme;
import com.netegrity.sdk.policyapi.SmUserDirectory;
import com.netegrity.sdk.policyapi.SmUserPolicy;
import com.netegrity.sdk.policyapi.SmVariable;
import com.netegrity.sdk.policyapi.SmVariableType;
import com.sso.apg.policy.bean.RealmBO;
import com.sso.apg.policy.bean.RuleBO;

public class SMPolicyGeneratorTest {

	private static final Logger logger = Logger.getLogger(SMPolicyGeneratorTest.class);
	public static void main(String args[]) throws IOException{
		
		SMConnectionUtil connUtil = null;
		try {
		connUtil = new SMConnectionUtil();
		SmApiResult smApiResult;


			if(connUtil.getConnection() != null){
				SMAgentUtil policyUtil = new SMAgentUtil();
				SmAgent agent = new SmAgent();
				smApiResult = policyUtil.getAgent(connUtil, "iam_web_agent", agent);
				logger.debug("Got agent ? " + smApiResult.isSuccess());
				logger.debug(agent.getOid() + " | " + agent.getAgentType());
				Hashtable<String, String> agentProp = new Hashtable<String, String>();
				agentProp.put("TrustedHost", "");
				agent.readProperties(agentProp);
				logger.debug(agentProp.get("TrustedHost"));
				SmUserDirectory dir = new SmUserDirectory();
				connUtil.getConnection().getUserDirectory("IAM User store", dir);
				//logger.debug(dir.);
				
				
				
				
				SMDomainUtil domainUtil = new SMDomainUtil();
				SmDomain domain = new SmDomain();
				smApiResult = domainUtil.getDomain(connUtil, "IAM Core Domain", domain);
				logger.debug("Got domain ? " + smApiResult.isSuccess());
				logger.debug("IAM Core domain OID >>>" + domain.getOid());
				
				//smApiResult = domainUtil.deleteDomain(connUtil, "Test API Domain1");
				
				SmDomain domain1 = new SmDomain();
				domain1.setDescription("Test API domain desc");
				domain1.setName("Test API Domainz");	
				domain1.setOid("123wsxcde");
				smApiResult = domainUtil.createDomian(connUtil, domain1);
				logger.debug("Domain created ? " + smApiResult.isSuccess());
				smApiResult = domainUtil.addUserDirToDomain(connUtil, domain1.getName(), "IAM User store");
				logger.debug("directory added ? " + smApiResult.isSuccess());
				logger.debug("domain OID >>>" +domain1.getOid());
				SmScheme authScheme = new SmScheme();
				connUtil.getConnection().getScheme("IAM Core Auth Scheme", authScheme);
				
				RealmBO realmBO = new RealmBO("test realm",agent,domain1,authScheme,"/test/resource");
				SMRealmUtil realmUtil = new SMRealmUtil();
				smApiResult = realmUtil.addRealm(connUtil, realmBO);
				logger.debug("Realm added ? " + smApiResult.isSuccess());
				//connUtil.getConnection().add
				//realmBO.showAll();
				
				//SmRule rules = new SmRule();
				//connUtil.getConnection().getRule("Access Accept", realmBO.getRealmBO().getOid().toString(), "IAM Core Domain", rules);
				//logger.debug(rules.getName() + " | " + rules.getDescription() + " | " + rules.getAgent() + " | " + rules.getRealm() + " | " + rules.getAllowAccess() + " | " + rules.getEnabled() + " | " + rules.getAction() + " | " +rules.getResource() + " | " + rules.getAgentType() + " | " + rules.getDomainName() + " | " + rules.getDomain());

				
				logger.debug(domain1.getOid().isNull());
				RuleBO ruleBO = new RuleBO(domain1.getOid(),"test rule", "test desc", realmBO.getRealm(), true, true, SmRule.ACTION_ON_AUTH_ACCEPT);
				logger.debug(ruleBO.getRule().getDomain().isNull());
				SMRuleUtil ruleUtil = new SMRuleUtil();
				smApiResult = ruleUtil.addRule(connUtil,ruleBO);
				
				//PolicyBO policyBO = new PolicyBO();
				
				SmPolicy policy = new SmPolicy();
				policy.setDomain(domain1.getOid());
				policy.setName("test policy");
				policy.setDescription("test policy desc");
				policy.setEnabled(true);
				connUtil.getConnection().addPolicy(policy);
				
				SmResponse response = new SmResponse();
				SmAgentType agentType = new SmAgentType();
				connUtil.getConnection().getAgentType("Web Agent", agentType);
				response.setName("test response");
				response.setDomain(domain1.getOid());
				response.setAgentType(agentType.getOid());
				
				connUtil.getConnection().addResponse(response);
				SmResponseAttr responseAttr = new SmResponseAttr();
				responseAttr.setDomain(domain1.getOid());
				responseAttr.setAgentTypeAttr(SmResponseAttr.ATTR_WA_HTTP_COOKIE_VARIABLE);
				responseAttr.setResponse(response.getOid());
				responseAttr.setValue("guidCookie=<% userattr=\"guid\" %>");
				connUtil.getConnection().addResponseAttr(responseAttr);
				
				
				SmPolicyLink policyLink = new SmPolicyLink();
				policyLink.setDomain(domain1.getOid());
				policyLink.setPolicy(policy.getOid());
				policyLink.setRule(ruleBO.getRule().getOid());
				policyLink.setResponse(response);
				connUtil.getConnection().addPolicyLink(policyLink);
				//policyLink.set
				SmUserDirectory userDirectory = new SmUserDirectory();
				connUtil.getConnection().getUserDirectory("IAM User Store", userDirectory);
				SmUserPolicy userPolicy = new SmUserPolicy();
				userPolicy.setDomain(domain1.getOid());
				userPolicy.setPolicy(policy.getOid());
				userPolicy.setFilterClass("organizationalunit");
				userPolicy.setFilterPath("ou=People, o=iam");
				userPolicy.setUserDirectory(userDirectory.getOid());
				connUtil.getConnection().addUserPolicy(userPolicy);
				
				
				
				
				SmExportAttr exportAttr = new SmExportAttr();
				exportAttr.setDomain("IAM Core Domain", false);
				exportAttr.setFileName("E:\\JAVA STUFFS\\APG\\output-brief.smdif", true);
				//exportAttr.set
				smApiResult = connUtil.getConnection().doExport(exportAttr);
				logger.debug("Export successful ? " + smApiResult.isSuccess());
				
				FileInputStream localFileInputStream = new FileInputStream("E:\\JAVA STUFFS\\APG\\output-brief.smdif");
				byte[] b = new byte[1];
				StringBuffer localBufferedFile = new StringBuffer();
				while(localFileInputStream.read(b) != -1){
					//logger.debug(new String(b));
					localBufferedFile.append(new String(b));
				}
				localFileInputStream.close();
				logger.debug(localBufferedFile.toString());
				//localBufferedFile.indexOf(str)
				/*try {
					FileOutputStream test = new FileOutputStream("E:\\JAVA STUFFS\\Auto Policy Generator\\test.smdif",true);
					byte[] writeIt = "Value: test=<% userattr=\"uid\" %>".getBytes("UTF-8"); 
					try {
						test.write(writeIt);
						test.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				//connUtil.getConnection().getu
				/*SmImportAttr importAttr = new SmImportAttr();
				importAttr.setFileName("E:\\JAVA STUFFS\\Auto Policy Generator\\output.smdif");
				importAttr.setOverWriteExistingDatabaseRecords();
				smApiResult = connUtil.getConnection().doImport(importAttr);
				logger.debug("Import successful ? " + smApiResult);*/ 
				
				SmVariable smVar = new SmVariable();
				smVar.setName("test");
				smVar.setDescription("test");
				smVar.setDomain(domain1.getOid());
				smVar.setVariableType(SmVariableType.USER_CONTEXT_VARIABLES);
				smVar.setPrefetchFlag(false);
				smVar.setDefinition("<ItemName>UserProperty</ItemName><PropertyName>uid</PropertyName><BufferSize>0</BufferSize>");
				connUtil.getConnection().addVariable(smVar);
				System.out.println(smVar.getOid());
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			try {
				SmApiResult logoutResult = connUtil.logout();
				logger.debug("logged out ? " + logoutResult.isSuccess());
			} catch (SmApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
