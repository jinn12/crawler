package com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class PublicationInfoArrayVO {
	List<PublicationInfoVO> publicationInfo = new ArrayList<PublicationInfoVO>();

	public List<PublicationInfoVO> getPublicationInfo() {
		return publicationInfo;
	}

	public void setPublicationInfo(List<PublicationInfoVO> publicationInfo) {
		this.publicationInfo = publicationInfo;
	}


}
