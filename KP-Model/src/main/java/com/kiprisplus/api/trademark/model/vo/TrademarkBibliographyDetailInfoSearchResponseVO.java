package com.kiprisplus.api.trademark.model.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.kiprisplus.api.common.model.vo.KiprisPlusResponseHeaderVO;
import com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.BiblioDetailSearchResponseBodyVO;


@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrademarkBibliographyDetailInfoSearchResponseVO {
	private KiprisPlusResponseHeaderVO header = new KiprisPlusResponseHeaderVO();
	private BiblioDetailSearchResponseBodyVO body = new BiblioDetailSearchResponseBodyVO();
	public KiprisPlusResponseHeaderVO getHeader() {
		return header;
	}
	public void setHeader(KiprisPlusResponseHeaderVO header) {
		this.header = header;
	}
	public BiblioDetailSearchResponseBodyVO getBody() {
		return body;
	}
	public void setBody(BiblioDetailSearchResponseBodyVO body) {
		this.body = body;
	}


}
