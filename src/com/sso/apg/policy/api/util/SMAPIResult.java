package com.sso.apg.policy.api.util;

import com.netegrity.sdk.apiutil.SmApiResult;

public class SMAPIResult {

	private SmApiResult result;
	private String operation;
	private String messages;
	private int reason;
	private boolean success;
	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;
	public SmApiResult getResult() {
		return result;
	}
	public void setResult(SmApiResult result) {
		this.result = result;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
