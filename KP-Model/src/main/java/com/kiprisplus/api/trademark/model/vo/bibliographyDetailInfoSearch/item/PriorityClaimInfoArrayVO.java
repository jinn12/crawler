package com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class PriorityClaimInfoArrayVO {
	List<PriorityClaimInfoVO> priorityClaimInfo = new ArrayList<PriorityClaimInfoVO>();

	public List<PriorityClaimInfoVO> getPriorityClaimInfo() {
		return priorityClaimInfo;
	}

	public void setPriorityClaimInfo(List<PriorityClaimInfoVO> priorityClaimInfo) {
		this.priorityClaimInfo = priorityClaimInfo;
	}


}
