package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class PriorArtDocumentsInfoArrayVO {
	private List<PriorArtDocumentsInfoVO> priorArtDocumentsInfo = new ArrayList<PriorArtDocumentsInfoVO>();

	public List<PriorArtDocumentsInfoVO> getPriorArtDocumentsInfo() {
		return priorArtDocumentsInfo;
	}

	public void setPriorArtDocumentsInfo(List<PriorArtDocumentsInfoVO> priorArtDocumentsInfo) {
		this.priorArtDocumentsInfo = priorArtDocumentsInfo;
	}

}
