package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class ClaimInfoArrayVO {
	private List<ClaimInfoVO> claimInfo = new ArrayList<ClaimInfoVO>();

	public List<ClaimInfoVO> getClaimInfo() {
		return claimInfo;
	}

	public void setClaimInfo(List<ClaimInfoVO> claimInfo) {
		this.claimInfo = claimInfo;
	}


}
