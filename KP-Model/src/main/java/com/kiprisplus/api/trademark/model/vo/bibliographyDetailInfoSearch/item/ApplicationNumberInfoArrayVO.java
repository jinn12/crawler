package com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class ApplicationNumberInfoArrayVO {
	List<ApplicationNumberInfoVO> applicationNumberInfo = new ArrayList<ApplicationNumberInfoVO>();

	public List<ApplicationNumberInfoVO> getApplicationNumberInfo() {
		return applicationNumberInfo;
	}

	public void setApplicationNumberInfo(List<ApplicationNumberInfoVO> applicationNumberInfo) {
		this.applicationNumberInfo = applicationNumberInfo;
	}

}
