package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch;

import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.AbstractInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.AgentInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.ApplicantInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.BiblioSummaryInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.ClaimInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.DesignatedStateInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.FamilyInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.ImagePathInfoVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.InternationalInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.InventorInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.IpcInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.LegalStatusInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.PriorArtDocumentsInfoArrayVO;
import com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item.PriorityInfoArrayVO;

public class BiblioDetailSearchResponseItemVO {
	BiblioSummaryInfoArrayVO biblioSummaryInfoArray = new BiblioSummaryInfoArrayVO();
	IpcInfoArrayVO ipcInfoArray = new IpcInfoArrayVO();
	FamilyInfoArrayVO familyInfoArray = new FamilyInfoArrayVO();
	AbstractInfoArrayVO abstractInfoArray = new AbstractInfoArrayVO();
	InternationalInfoArrayVO internationalInfoArray = new InternationalInfoArrayVO();
	ClaimInfoArrayVO claimInfoArray = new ClaimInfoArrayVO();
	ApplicantInfoArrayVO applicantInfoArray = new ApplicantInfoArrayVO();
	InventorInfoArrayVO inventorInfoArray = new InventorInfoArrayVO();
	AgentInfoArrayVO agentInfoArray = new AgentInfoArrayVO();
	PriorityInfoArrayVO priorityInfoArray = new PriorityInfoArrayVO();
	DesignatedStateInfoArrayVO designatedStateInfoArray = new DesignatedStateInfoArrayVO();
	PriorArtDocumentsInfoArrayVO priorArtDocumentsInfoArray = new PriorArtDocumentsInfoArrayVO();
	LegalStatusInfoArrayVO legalStatusInfoArray = new LegalStatusInfoArrayVO();
	ImagePathInfoVO imagePathInfo= new ImagePathInfoVO();
	public BiblioSummaryInfoArrayVO getBiblioSummaryInfoArray() {
		return biblioSummaryInfoArray;
	}
	public void setBiblioSummaryInfoArray(BiblioSummaryInfoArrayVO biblioSummaryInfoArray) {
		this.biblioSummaryInfoArray = biblioSummaryInfoArray;
	}
	public IpcInfoArrayVO getIpcInfoArray() {
		return ipcInfoArray;
	}
	public void setIpcInfoArray(IpcInfoArrayVO ipcInfoArray) {
		this.ipcInfoArray = ipcInfoArray;
	}
	public FamilyInfoArrayVO getFamilyInfoArray() {
		return familyInfoArray;
	}
	public void setFamilyInfoArray(FamilyInfoArrayVO familyInfoArray) {
		this.familyInfoArray = familyInfoArray;
	}
	public AbstractInfoArrayVO getAbstractInfoArray() {
		return abstractInfoArray;
	}
	public void setAbstractInfoArray(AbstractInfoArrayVO abstractInfoArray) {
		this.abstractInfoArray = abstractInfoArray;
	}
	public InternationalInfoArrayVO getInternationalInfoArray() {
		return internationalInfoArray;
	}
	public void setInternationalInfoArray(InternationalInfoArrayVO internationalInfoArray) {
		this.internationalInfoArray = internationalInfoArray;
	}
	public ClaimInfoArrayVO getClaimInfoArray() {
		return claimInfoArray;
	}
	public void setClaimInfoArray(ClaimInfoArrayVO claimInfoArray) {
		this.claimInfoArray = claimInfoArray;
	}
	public ApplicantInfoArrayVO getApplicantInfoArray() {
		return applicantInfoArray;
	}
	public void setApplicantInfoArray(ApplicantInfoArrayVO applicantInfoArray) {
		this.applicantInfoArray = applicantInfoArray;
	}
	public InventorInfoArrayVO getInventorInfoArray() {
		return inventorInfoArray;
	}
	public void setInventorInfoArray(InventorInfoArrayVO inventorInfoArray) {
		this.inventorInfoArray = inventorInfoArray;
	}
	public AgentInfoArrayVO getAgentInfoArray() {
		return agentInfoArray;
	}
	public void setAgentInfoArray(AgentInfoArrayVO agentInfoArray) {
		this.agentInfoArray = agentInfoArray;
	}
	public PriorityInfoArrayVO getPriorityInfoArray() {
		return priorityInfoArray;
	}
	public void setPriorityInfoArray(PriorityInfoArrayVO priorityInfoArray) {
		this.priorityInfoArray = priorityInfoArray;
	}
	public DesignatedStateInfoArrayVO getDesignatedStateInfoArray() {
		return designatedStateInfoArray;
	}
	public void setDesignatedStateInfoArray(DesignatedStateInfoArrayVO designatedStateInfoArray) {
		this.designatedStateInfoArray = designatedStateInfoArray;
	}
	public PriorArtDocumentsInfoArrayVO getPriorArtDocumentsInfoArray() {
		return priorArtDocumentsInfoArray;
	}
	public void setPriorArtDocumentsInfoArray(PriorArtDocumentsInfoArrayVO priorArtDocumentsInfoArray) {
		this.priorArtDocumentsInfoArray = priorArtDocumentsInfoArray;
	}
	public LegalStatusInfoArrayVO getLegalStatusInfoArray() {
		return legalStatusInfoArray;
	}
	public void setLegalStatusInfoArray(LegalStatusInfoArrayVO legalStatusInfoArray) {
		this.legalStatusInfoArray = legalStatusInfoArray;
	}
	public ImagePathInfoVO getImagePathInfo() {
		return imagePathInfo;
	}
	public void setImagePathInfo(ImagePathInfoVO imagePathInfo) {
		this.imagePathInfo = imagePathInfo;
	}



}
