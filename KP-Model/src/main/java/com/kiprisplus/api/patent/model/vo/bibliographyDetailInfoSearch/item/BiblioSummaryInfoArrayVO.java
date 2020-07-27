package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class BiblioSummaryInfoArrayVO {
	private List<BiblioSummaryInfoVO> biblioSummaryInfo = new ArrayList<BiblioSummaryInfoVO>();

	public List<BiblioSummaryInfoVO> getBiblioSummaryInfo() {
		return biblioSummaryInfo;
	}

	public void setBiblioSummaryInfo(List<BiblioSummaryInfoVO> biblioSummaryInfo) {
		this.biblioSummaryInfo = biblioSummaryInfo;
	}


}
