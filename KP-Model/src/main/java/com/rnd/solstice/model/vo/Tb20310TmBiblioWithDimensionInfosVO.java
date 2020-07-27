package com.rnd.solstice.model.vo;

import java.util.ArrayList;
import java.util.List;


public class Tb20310TmBiblioWithDimensionInfosVO extends Tb20300TmDbVO {
	List<Tb20310ProductDetailVO> prodeuctDetailInfoList = new ArrayList<Tb20310ProductDetailVO>();

	public List<Tb20310ProductDetailVO> getProdeuctDetailInfoList() {
		return prodeuctDetailInfoList;
	}

	public void setProdeuctDetailInfoList(List<Tb20310ProductDetailVO> prodeuctDetailInfoList) {
		this.prodeuctDetailInfoList = prodeuctDetailInfoList;
	}



}
