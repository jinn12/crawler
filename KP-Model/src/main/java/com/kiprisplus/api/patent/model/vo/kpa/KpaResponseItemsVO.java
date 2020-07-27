package com.kiprisplus.api.patent.model.vo.kpa;

public class KpaResponseItemsVO {
	KpaResponseSummationVO summation = new KpaResponseSummationVO();

	public KpaResponseSummationVO getSummation() {
		return summation;
	}

	public void setSummation(KpaResponseSummationVO summation) {
		this.summation = summation;
	}

}
