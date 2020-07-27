package com.kiprisplus.api.patent.model.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.kiprisplus.api.common.model.vo.KiprisPlusResponseHeaderVO;
import com.kiprisplus.api.patent.model.vo.kpa.KpaResponseBodyVO;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class KpaResponseVO {
	private KiprisPlusResponseHeaderVO header = new KiprisPlusResponseHeaderVO();

	private KpaResponseBodyVO body = new KpaResponseBodyVO();

	public KiprisPlusResponseHeaderVO getHeader() {
		return header;
	}

	public void setHeader(KiprisPlusResponseHeaderVO header) {
		this.header = header;
	}

	public KpaResponseBodyVO getBody() {
		return body;
	}

	public void setBody(KpaResponseBodyVO body) {
		this.body = body;
	}


}
