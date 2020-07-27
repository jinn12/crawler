package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class IpcInfoArrayVO {
	private List<IpcInfoVO> ipcInfo = new ArrayList<IpcInfoVO>();

	public List<IpcInfoVO> getIpcInfo() {
		return ipcInfo;
	}

	public void setIpcInfo(List<IpcInfoVO> ipcInfo) {
		this.ipcInfo = ipcInfo;
	}


}
