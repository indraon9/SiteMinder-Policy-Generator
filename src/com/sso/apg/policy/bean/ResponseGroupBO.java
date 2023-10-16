package com.sso.apg.policy.bean;

import java.util.ArrayList;
import java.util.List;

public class ResponseGroupBO {

	private List <ResponseBO> responseList = new ArrayList<ResponseBO>();

	public List <ResponseBO> getResponseList() {
		return responseList;
	}

	public void setResponseList(List <ResponseBO> responseList) {
		this.responseList = responseList;
	}
	
	public void addResponseToList(ResponseBO response){
		this.responseList.add(response);
	}
	
}

