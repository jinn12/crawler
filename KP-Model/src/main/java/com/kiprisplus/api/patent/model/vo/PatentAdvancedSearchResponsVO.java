package com.kiprisplus.api.patent.model.vo;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.kiprisplus.api.common.model.vo.KipirsPlusReponse;
import com.kiprisplus.api.common.model.vo.KiprisPlusResponseCountVO;
import com.kiprisplus.api.common.model.vo.KiprisPlusResponseHeaderVO;
import com.kiprisplus.api.patent.model.vo.advancedSearch.AdSearchResponseBodyVO;


@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatentAdvancedSearchResponsVO extends KipirsPlusReponse {

	private KiprisPlusResponseHeaderVO header = new KiprisPlusResponseHeaderVO();
	private KiprisPlusResponseCountVO count = new KiprisPlusResponseCountVO();
	private AdSearchResponseBodyVO body = new AdSearchResponseBodyVO();

	public KiprisPlusResponseHeaderVO getHeader() {
		return header;
	}

	public void setHeader(KiprisPlusResponseHeaderVO header) {
		this.header = header;
	}

	public KiprisPlusResponseCountVO getCount() {
		return count;
	}

	public void setCount(KiprisPlusResponseCountVO count) {
		this.count = count;
	}

	public AdSearchResponseBodyVO getBody() {
		return body;
	}

	public void setBody(AdSearchResponseBodyVO body) {
		this.body = body;
	}




}
