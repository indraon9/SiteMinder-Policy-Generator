package com.sso.apg.policy.api.util;

import java.util.ArrayList;
import java.util.List;

public class SMAPIResultList {

	private List <SMAPIResult>  apiResult = new ArrayList<SMAPIResult>();
	private boolean success;
	private String task;
	public List <SMAPIResult> getApiResult() {
		return apiResult;
	}

	public void add(SMAPIResult result){
		this.apiResult.add(result);
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
