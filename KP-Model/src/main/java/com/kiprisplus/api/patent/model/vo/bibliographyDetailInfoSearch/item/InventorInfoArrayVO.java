package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class InventorInfoArrayVO {
	private List<InventorInfoVO> inventorInfo = new ArrayList<InventorInfoVO>();

	public List<InventorInfoVO> getInventorInfo() {
		return inventorInfo;
	}

	public void setInventorInfo(List<InventorInfoVO> inventorInfo) {
		this.inventorInfo = inventorInfo;
	}

}
