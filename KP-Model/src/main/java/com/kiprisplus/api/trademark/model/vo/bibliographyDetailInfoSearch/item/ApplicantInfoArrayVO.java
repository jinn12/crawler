package com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class ApplicantInfoArrayVO {
	List<ApplicantInfoVO> applicantInfo = new ArrayList<ApplicantInfoVO>();

	public List<ApplicantInfoVO> getApplicantInfo() {
		return applicantInfo;
	}

	public void setApplicantInfo(List<ApplicantInfoVO> applicantInfo) {
		this.applicantInfo = applicantInfo;
	}

}
