package com.kiprisplus.api.patent.model.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.kiprisplus.api.common.model.vo.KiprisPlusResponseHeaderVO;
import com.kiprisplus.api.patent.model.vo.cpcInfo.CpcInfoResponseBodyVO;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatentCpcInfoResponseVO {
	private KiprisPlusResponseHeaderVO header = new KiprisPlusResponseHeaderVO();
	private CpcInfoResponseBodyVO body = new CpcInfoResponseBodyVO();

	public KiprisPlusResponseHeaderVO getHeader() {
		return header;
	}
	public void setHeader(KiprisPlusResponseHeaderVO header) {
		this.header = header;
	}
	public CpcInfoResponseBodyVO getBody() {
		return body;
	}
	public void setBody(CpcInfoResponseBodyVO body) {
		this.body = body;
	}


}
