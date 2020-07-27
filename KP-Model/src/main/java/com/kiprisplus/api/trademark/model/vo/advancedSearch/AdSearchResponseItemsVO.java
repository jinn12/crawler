package com.kiprisplus.api.trademark.model.vo.advancedSearch;

import java.util.ArrayList;
import java.util.List;

public class AdSearchResponseItemsVO {
	List<AdSearchResponseItemVO> item = new ArrayList<AdSearchResponseItemVO>();

	public List<AdSearchResponseItemVO> getItem() {
		return item;
	}

	public void setItem(List<AdSearchResponseItemVO> item) {
		this.item = item;
	}
}
