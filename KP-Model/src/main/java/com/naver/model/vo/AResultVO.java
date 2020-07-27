package com.naver.model.vo;

import java.util.ArrayList;
import java.util.List;

public class AResultVO {
	private String sFirstName;

	private List<AItemsVO> aItems = new ArrayList<AItemsVO>();

	public String getsFirstName() {
		return sFirstName;
	}

	public void setsFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}

	public List<AItemsVO> getaItems() {
		return aItems;
	}

	public void setaItems(List<AItemsVO> aItems) {
		this.aItems = aItems;
	}


}
