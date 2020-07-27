package com.kiprisplus.api.patent.model.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.kiprisplus.api.common.model.vo.KiprisPlusResponseHeaderVO;
import com.kiprisplus.api.patent.model.vo.changeInfoSearch.ChangeInfoSearchResponseBodyVO;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatentChangeInfoSerarchVO {
	private KiprisPlusResponseHeaderVO header = new KiprisPlusResponseHeaderVO();
	private ChangeInfoSearchResponseBodyVO body = new ChangeInfoSearchResponseBodyVO();
	public KiprisPlusResponseHeaderVO getHeader() {
		return header;
	}
	public void setHeader(KiprisPlusResponseHeaderVO header) {
		this.header = header;
	}
	public ChangeInfoSearchResponseBodyVO getBody() {
		return body;
	}
	public void setBody(ChangeInfoSearchResponseBodyVO body) {
		this.body = body;
	}


}
