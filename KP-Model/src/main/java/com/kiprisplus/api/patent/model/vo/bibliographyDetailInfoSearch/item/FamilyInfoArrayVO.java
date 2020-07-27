package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class FamilyInfoArrayVO {
	private List<FamilyInfoVO> familyInfo = new ArrayList<FamilyInfoVO>();

	public List<FamilyInfoVO> getFamilyInfo() {
		return familyInfo;
	}

	public void setFamilyInfo(List<FamilyInfoVO> familyInfo) {
		this.familyInfo = familyInfo;
	}


}
