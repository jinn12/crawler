package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class InternationalInfoArrayVO {
	private List<InternationalInfoVO> internationalInfo = new ArrayList<InternationalInfoVO>();

	public List<InternationalInfoVO> getInternationalInfo() {
		return internationalInfo;
	}

	public void setInternationalInfo(List<InternationalInfoVO> internationalInfo) {
		this.internationalInfo = internationalInfo;
	}

}
