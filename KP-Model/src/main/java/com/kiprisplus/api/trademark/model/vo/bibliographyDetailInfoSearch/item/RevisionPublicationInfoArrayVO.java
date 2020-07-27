package com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class RevisionPublicationInfoArrayVO {
	List<RevisionPublicationInfoVO> revisionPublicationInfo = new ArrayList<RevisionPublicationInfoVO>();

	public List<RevisionPublicationInfoVO> getRevisionPublicationInfo() {
		return revisionPublicationInfo;
	}

	public void setRevisionPublicationInfo(List<RevisionPublicationInfoVO> revisionPublicationInfo) {
		this.revisionPublicationInfo = revisionPublicationInfo;
	}


}
