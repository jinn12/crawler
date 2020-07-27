package com.naver.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageVO {
	@JsonProperty("@type")
	private String type;
	@JsonProperty("@service")
	private String service;
	@JsonProperty("@version")
	private String version;

	private ResultVO result = new ResultVO();

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public ResultVO getResult() {
		return result;
	}
	public void setResult(ResultVO result) {
		this.result = result;
	}


}
