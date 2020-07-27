package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class PriorityInfoArrayVO {
	private List<PriorityInfoVO> priorityInfo = new ArrayList<PriorityInfoVO>();

	public List<PriorityInfoVO> getPriorityInfo() {
		return priorityInfo;
	}

	public void setPriorityInfo(List<PriorityInfoVO> priorityInfo) {
		this.priorityInfo = priorityInfo;
	}

}
