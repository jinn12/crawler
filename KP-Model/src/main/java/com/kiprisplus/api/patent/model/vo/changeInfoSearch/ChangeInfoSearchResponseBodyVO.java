package com.kiprisplus.api.patent.model.vo.changeInfoSearch;

public class ChangeInfoSearchResponseBodyVO {
	//키프리스플러스자체 오류 item을 itme로 사용하고 있음.ㅡㅡ
	ChangeInfoSearchResponseItemVO itme = new ChangeInfoSearchResponseItemVO();

	public ChangeInfoSearchResponseItemVO getItme() {
		return itme;
	}

	public void setItme(ChangeInfoSearchResponseItemVO itme) {
		this.itme = itme;
	}



}
