package com.kiprisplus.api.trademark.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class SimilarityCodeInfoArrayVO {
	List<SimilarityCodeInfoVO> similarityCodeInfo = new ArrayList<SimilarityCodeInfoVO>();

	public List<SimilarityCodeInfoVO> getSimilarityCodeInfo() {
		return similarityCodeInfo;
	}

	public void setSimilarityCodeInfo(List<SimilarityCodeInfoVO> similarityCodeInfo) {
		this.similarityCodeInfo = similarityCodeInfo;
	}

}
