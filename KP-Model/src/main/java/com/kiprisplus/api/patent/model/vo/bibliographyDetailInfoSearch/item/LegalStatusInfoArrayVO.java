package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class LegalStatusInfoArrayVO {
	private List<LegalStatusInfoVO> legalStatusInfo = new ArrayList<LegalStatusInfoVO>();

	public List<LegalStatusInfoVO> getLegalStatusInfo() {
		return legalStatusInfo;
	}

	public void setLegalStatusInfo(List<LegalStatusInfoVO> legalStatusInfo) {
		this.legalStatusInfo = legalStatusInfo;
	}


}
