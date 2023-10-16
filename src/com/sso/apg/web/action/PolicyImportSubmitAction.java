package com.sso.apg.web.action;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netegrity.sdk.apiutil.SmApiException;
import com.sso.apg.policy.api.impl.APGImportDomainImpl;
import com.sso.apg.policy.api.intf.APGPolicyAPI;
import com.sso.apg.policy.api.util.SMAPIResultEnum;
import com.sso.apg.policy.api.util.SMAPIResultList;
import com.sso.apg.policy.bean.DomainBO;
import com.sso.apg.policy.bean.ImportObjectBO;
import com.sso.apg.web.bean.ImportFormBO;
import com.sso.apg.web.bean.ImportObject;
import com.sso.apg.web.util.WebUtil;

public class PolicyImportSubmitAction extends Action {

	private static final Logger logger = Logger.getLogger(PolicyImportSubmitAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		try {
		ImportFormBO localForm = (ImportFormBO) form;
		boolean tempFlag = false;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		logger.debug("Is file upload success ? " + WebUtil.uploadFile("E:" + File.separator + "JAVA STUFFS" + File.separator + "APG" + File.separator + "upload" + File.separator, localForm.getImportFile()));
		//logger.debug("Unzipped ? " + WebUtil.unzip(getServlet().getServletContext().getRealPath(File.separator) + "upload", localForm.getImportFile().getFileName()));
		DomainBO domainBO = new DomainBO();
		domainBO.setImportFile("E:" + File.separator + "JAVA STUFFS" + File.separator + "APG" + File.separator + "upload" + File.separator + localForm.getImportFile().getFileName());
		domainBO.setCustomExport(true);
		String temp = (localForm.getNotFoundObjects() == null)?"nfObj is null":"nfObj size: " +localForm.getNotFoundObjects().size();
		logger.debug(temp);
		if(localForm.getNotFoundObjects() != null && localForm.getNotFoundObjects().size() != 0){
			for(int i = 0; i<localForm.getNotFoundObjects().size() ; i++){
				ImportObjectBO localObj = new ImportObjectBO(localForm.getNotFoundObjects().get(i).getObjectType(), localForm.getNotFoundObjects().get(i).getOldObjName(),localForm.getNotFoundObjects().get(i).getUpdatedObjName());
				logger.debug("printing localObj : " + localObj.getObjectType() + localObj.getOldObjName() + localObj.getUpdatedObjName());
				domainBO.addObjects(localObj);
			}
		}
			localForm.resetAll(mapping, request);
			APGPolicyAPI policyApi = new APGImportDomainImpl();
			SMAPIResultList resultList = policyApi.processAll(domainBO);
			if(resultList.isSuccess()){
				messages.add("success", new ActionMessage("import.task.success.message"));
			}else{
			for(int i = 0 ; i<resultList.getApiResult().size() ; i++){
				
				if(!resultList.getApiResult().get(i).isSuccess()){
					if(resultList.getApiResult().get(i).getReason() == SMAPIResultEnum.agent_object_not_found.getReasonCode()){
						ImportObject localObj = new ImportObject();
						localObj.setObjectType("Agent");
						localObj.setOldObjName(resultList.getApiResult().get(i).getMessages());
						localObj.setUpdatedObjName("");
						localForm.addObjects(localObj);
						tempFlag = true;
					}else if(resultList.getApiResult().get(i).getReason() == SMAPIResultEnum.agentType_object_not_found.getReasonCode()){
						ImportObject localObj = new ImportObject();
						localObj.setObjectType("Agent type");
						localObj.setOldObjName(resultList.getApiResult().get(i).getMessages());
						localObj.setUpdatedObjName("");
						localForm.addObjects(localObj);
						tempFlag = true;
					}else if(resultList.getApiResult().get(i).getReason() == SMAPIResultEnum.agentTypeAttr_object_not_found.getReasonCode()){
						ImportObject localObj = new ImportObject();
						localObj.setObjectType("Agent type attribute");
						localObj.setOldObjName(resultList.getApiResult().get(i).getMessages());
						localObj.setUpdatedObjName("");
						localForm.addObjects(localObj);
						tempFlag = true;
					}else if(resultList.getApiResult().get(i).getReason() == SMAPIResultEnum.authScheme_object_not_found.getReasonCode()){
						ImportObject localObj = new ImportObject();
						localObj.setObjectType("Auth Scheme");
						localObj.setOldObjName(resultList.getApiResult().get(i).getMessages());
						localObj.setUpdatedObjName("");
						localForm.addObjects(localObj);
						tempFlag = true;
					}else if(resultList.getApiResult().get(i).getReason() == SMAPIResultEnum.userDir_object_not_found.getReasonCode()){
						ImportObject localObj = new ImportObject();
						localObj.setObjectType("User Directory");
						localObj.setOldObjName(resultList.getApiResult().get(i).getMessages());
						localObj.setUpdatedObjName("");
						localForm.addObjects(localObj);
						tempFlag = true;
					}else if(resultList.getApiResult().get(i).getReason() == SMAPIResultEnum.importTask_domain_already_exists.getReasonCode()){
						errors.add("error.importTask.domain.exists", new ActionMessage("import.task.error.domain.exists"));
					}
				}
			}
			}
			if(tempFlag){
				errors.add("error.importTask.object.notFound", new ActionMessage("import.task.error.object.notFound"));
			}
			saveErrors(request, errors);
			saveMessages(request, messages);
			return mapping.findForward("importPolicyForm");
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

	/*@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ImportFormBO localForm = (ImportFormBO) form;

		DomainBO domainBO = new DomainBO();
		domainBO.setDomainName(localForm.getDomainName());
		domainBO.setCustomExport(true);
		domainBO.setExportFile("E:\\JAVA STUFFS\\APG\\output.smdif");
		try {
			APGPolicyAPI policyApi = new APGExportDomainImpl();
			policyApi.processAll(domainBO);
			String [] fileName = new String[2];
			fileName[0] = domainBO.getExportFile();
			fileName[1] = domainBO.getExportFile().replace(".smdif", ".cfg");
			//String zipFileName = "E:\\JAVA STUFFS\\APG\\export_" + domainBO.getDomainName().replaceAll(" ", "_").toLowerCase() + ".zip";
			String zipFileName = "E:\\JAVA STUFFS\\APG\\tmp.zip";
			boolean isZipped = WebUtil.makeZip(fileName, zipFileName);
			logger.debug("Zipped ? " + isZipped);
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
	}*/
}
