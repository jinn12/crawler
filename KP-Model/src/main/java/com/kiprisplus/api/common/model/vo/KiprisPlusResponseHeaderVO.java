package com.kiprisplus.api.common.model.vo;

public class KiprisPlusResponseHeaderVO {
	private String requestMsgID;
	private String responseTime;
	private String responseMsgID;
	private String successYN;
	private String resultCode;
	private String resultMsg;
	public String getRequestMsgID() {
		return requestMsgID;
	}
	public void setRequestMsgID(String requestMsgID) {
		this.requestMsgID = requestMsgID;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getResponseMsgID() {
		return responseMsgID;
	}
	public void setResponseMsgID(String responseMsgID) {
		this.responseMsgID = responseMsgID;
	}
	public String getSuccessYN() {
		return successYN;
	}
	public void setSuccessYN(String successYN) {
		this.successYN = successYN;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}


}
