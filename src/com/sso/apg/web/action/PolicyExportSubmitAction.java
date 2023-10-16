package com.sso.apg.web.action;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.netegrity.sdk.apiutil.SmApiException;
import com.sso.apg.policy.api.impl.APGExportDomainImpl;
import com.sso.apg.policy.api.intf.APGPolicyAPI;
import com.sso.apg.policy.bean.DomainBO;
import com.sso.apg.web.bean.ExportFormBO;
import com.sso.apg.web.util.WebUtil;

public class PolicyExportSubmitAction extends DownloadAction {

	private static final Logger logger = Logger.getLogger(PolicyExportSubmitAction.class);
	/*public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		ExportFormBO localForm = (ExportFormBO) form;
		
		DomainBO domainBO = new DomainBO();
		domainBO.setDomainName(localForm.getDomainName());
		domainBO.setCustomExport(true);
		domainBO.setExportFile("E:\\JAVA STUFFS\\Auto Policy Generator\\output.smdif");
		try {
			APGPolicyAPI policyApi = new APGExportDomainImpl();
			policyApi.processAll(domainBO);
			//response.setContentType();
			
			return mapping.findForward("exportPolicyForm");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
ExportFormBO localForm = (ExportFormBO) form;
		
		DomainBO domainBO = new DomainBO();
		domainBO.setDomainName(localForm.getDomainName());
		domainBO.setCustomExport(true);
		domainBO.setExportFile("E:" + File.separator + "JAVA STUFFS" + File.separator + "APG" + File.separator  + "download" + File.separator + "output" + (new Date()).getTime() + ".smdif");
		boolean isCreated = WebUtil.makeDir("E:" + File.separator + "JAVA STUFFS" + File.separator + "APG" + File.separator + "download");
		logger.debug("download directory creeated : " + isCreated);
		try {
			APGPolicyAPI policyApi = new APGExportDomainImpl();
			policyApi.processAll(domainBO);
			/*String [] fileName = new String[2];
			fileName[0] = domainBO.getExportFile();
			fileName[1] = domainBO.getExportFile().replace(".smdif", ".cfg");
			//String zipFileName = "E:\\JAVA STUFFS\\APG\\export_" + domainBO.getDomainName().replaceAll(" ", "_").toLowerCase() + ".zip";
			String zipFileName = "E:\\JAVA STUFFS\\APG\\tmp.zip";
			boolean isZipped = WebUtil.makeZip(fileName, zipFileName);
			logger.debug("Zipped ? " + isZipped);*/
			response.setContentType("text/plain");
			File localFile = new File(domainBO.getExportZipFile());
			response.setHeader("Content-disposition", "attachment; filename="
	                + "export_" + domainBO.getDomainName().replaceAll(" ", "_").toLowerCase() + ".zip");
			return new FileStreamInfo("Content-disposition", localFile);
			//return mapping.findForward("exportPolicyForm");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
