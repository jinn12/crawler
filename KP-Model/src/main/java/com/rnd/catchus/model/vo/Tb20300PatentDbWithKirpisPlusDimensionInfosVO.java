package com.rnd.catchus.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.AgentInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.ApplicantInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.InventorInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.IpcInfoVO;
import com.kiprisplus.api.patent.model.vo.cpcInfo.PatentCpcInfoVO;

public class Tb20300PatentDbWithKirpisPlusDimensionInfosVO extends Tb20300PatentDbVO {
	List<IpcInfoVO> ipcInfoList  = new ArrayList<IpcInfoVO>();
	List<ApplicantInfoVO> applicantInfoList = new ArrayList<ApplicantInfoVO>();
	List<InventorInfoVO> inventorInfoList = new ArrayList<InventorInfoVO>();
	List<AgentInfoVO> agentInfoList = new ArrayList<AgentInfoVO>();
	List<PatentCpcInfoVO> cpcInfoList = new ArrayList<PatentCpcInfoVO>();

	public List<IpcInfoVO> getIpcInfoList() {
		return ipcInfoList;
	}
	public void setIpcInfoList(List<IpcInfoVO> ipcInfoList) {
		this.ipcInfoList = ipcInfoList;
	}
	public List<ApplicantInfoVO> getApplicantInfoList() {
		return applicantInfoList;
	}
	public void setApplicantInfoList(List<ApplicantInfoVO> applicantInfoList) {
		this.applicantInfoList = applicantInfoList;
	}
	public List<InventorInfoVO> getInventorInfoList() {
		return inventorInfoList;
	}
	public void setInventorInfoList(List<InventorInfoVO> inventorInfoList) {
		this.inventorInfoList = inventorInfoList;
	}
	public List<AgentInfoVO> getAgentInfoList() {
		return agentInfoList;
	}
	public void setAgentInfoList(List<AgentInfoVO> agentInfoList) {
		this.agentInfoList = agentInfoList;
	}
	public List<PatentCpcInfoVO> getCpcInfoList() {
		return cpcInfoList;
	}
	public void setCpcInfoList(List<PatentCpcInfoVO> cpcInfoList) {
		this.cpcInfoList = cpcInfoList;
	}


}
