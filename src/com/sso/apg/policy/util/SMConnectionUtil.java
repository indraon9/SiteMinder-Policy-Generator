package com.sso.apg.policy.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.netegrity.sdk.apiutil.SmApiConnection;
import com.netegrity.sdk.apiutil.SmApiException;
import com.netegrity.sdk.apiutil.SmApiResult;
import com.netegrity.sdk.apiutil.SmApiSession;
import com.netegrity.sdk.policyapi.SmPolicyApi;
import com.netegrity.sdk.policyapi.SmPolicyApiImpl;

import netegrity.siteminder.javaagent.AgentAPI;
import netegrity.siteminder.javaagent.InitDef;
import netegrity.siteminder.javaagent.ServerDef;

public class SMConnectionUtil {

	private static final Logger logger = Logger.getLogger(SMConnectionUtil.class);
	private SmApiSession apiSession = null;
	private SmPolicyApi policyApi;
	private String web_agent = "iam_web_agent";
	private String secret = "siteminder";
	private String admin = "siteminder";
	private String adminPwd = "siteminder";
	public SMConnectionUtil() throws UnknownHostException, SmApiException{
		this.setSmPolicyApi();
	}
	
	public SMConnectionUtil(String admin, String adminPwd) throws UnknownHostException, SmApiException{
		this.admin = admin;
		this.adminPwd = adminPwd;
		
		this.setSmPolicyApi();
	}
	
	private AgentAPI getAPI(){
		//System.setProperty("security.provider.1", "com.sun.crypto.provider.SunJCE");
		//logger.debug(System.getProperty("security.provider.1"));
		/*System.setProperty("java.library.path", "C:\\Windows;D:\\Indranil\\MFA\\PolicyGenerator\\dll\\smcommonutil.dll;D:\\Indranil\\MFA\\PolicyGenerator\\JNI;");*/
/*		logger.debug(System.getProperty("java.library.path"));*/
		AgentAPI api = new AgentAPI();
		ServerDef serverdef = new ServerDef();
		//serverdef.serverIpAddress="192.168.222.135";
		serverdef.serverIpAddress="localhost";
		serverdef.connectionMin= 1;
		serverdef.connectionMax=5;
		serverdef.connectionStep=1;
		serverdef.timeout=75;
		serverdef.authorizationPort=44443;
		serverdef.authenticationPort=44443;
		serverdef.accountingPort=44443;
		InitDef initDef = new InitDef(web_agent,secret,false,serverdef);
		
		int retCode = api.init(initDef);
		logger.debug("API Connected ? " + (retCode==AgentAPI.SUCCESS));
		return api;
		
	}
	
	private SmApiConnection getSmApiConnection(){
		return new SmApiConnection(getAPI());
	}
	
	private SmApiSession getSmApiSession(){
		apiSession = new SmApiSession(getSmApiConnection());
		return apiSession;
	}
	
	private SmApiResult apiLogin(SmApiSession apiSession) throws UnknownHostException, SmApiException{
		return apiSession.login(admin,adminPwd,InetAddress.getLocalHost(),0);
	}
	private void setSmPolicyApi() throws UnknownHostException, SmApiException{
		apiSession = getSmApiSession();
		SmApiResult loginResult = apiLogin(apiSession);
		logger.debug("Admin logged in ? " + loginResult.isSuccess());
		if(loginResult.isSuccess())
			policyApi = new SmPolicyApiImpl (apiSession);
		else 
			policyApi = null;

	}

	public SmApiResult logout() throws SmApiException {
		// TODO Auto-generated method stub
		return apiSession.logout();
	}

	protected SmPolicyApi getConnection() {
		return policyApi;
	}


}
