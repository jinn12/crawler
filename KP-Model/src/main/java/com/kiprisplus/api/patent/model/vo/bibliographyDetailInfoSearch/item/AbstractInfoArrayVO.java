package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class AbstractInfoArrayVO {
	private List<AbstractInfoVO> abstractInfo = new ArrayList<AbstractInfoVO>();

	public List<AbstractInfoVO> getAbstractInfo() {
		return abstractInfo;
	}

	public void setAbstractInfo(List<AbstractInfoVO> abstractInfo) {
		this.abstractInfo = abstractInfo;
	}


}
