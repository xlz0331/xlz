package com.hwagain.org.util;

public class ResultMessage {

	private int status;
	
	private String requestContent;
	
	private String resultContent;
	
	public ResultMessage() {
		super();
	}

	public ResultMessage(int status, String resultContent) {
		super();
		this.status = status;
		this.resultContent = resultContent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
}
