package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class DesignatedStateInfoArrayVO {
	private List<DesignatedStateInfoVO> designatedStateInfo = new ArrayList<DesignatedStateInfoVO>();

	public List<DesignatedStateInfoVO> getDesignatedStateInfo() {
		return designatedStateInfo;
	}

	public void setDesignatedStateInfo(List<DesignatedStateInfoVO> designatedStateInfo) {
		this.designatedStateInfo = designatedStateInfo;
	}


}
