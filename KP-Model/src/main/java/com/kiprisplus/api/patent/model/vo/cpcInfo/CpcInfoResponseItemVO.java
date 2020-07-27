package com.kiprisplus.api.patent.model.vo.cpcInfo;

import java.util.ArrayList;
import java.util.List;

public class CpcInfoResponseItemVO {
	List<PatentCpcInfoVO> patentCpcInfo = new ArrayList<PatentCpcInfoVO>();

	public List<PatentCpcInfoVO> getPatentCpcInfo() {
		return patentCpcInfo;
	}

	public void setPatentCpcInfo(List<PatentCpcInfoVO> patentCpcInfo) {
		this.patentCpcInfo = patentCpcInfo;
	}

}
