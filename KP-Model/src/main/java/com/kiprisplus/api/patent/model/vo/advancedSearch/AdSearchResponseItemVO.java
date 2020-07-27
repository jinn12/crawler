package com.kiprisplus.api.patent.model.vo.advancedSearch;

public class AdSearchResponseItemVO {
	private String indexNo;
 	private String registerStatus;
 	private String inventionTitle;
 	private String ipcNumber;
 	private String registerNumber;
 	private String registerDate;
 	private String applicationNumber;
 	private String applicationDate;
 	private String openNumber;
 	private String openDate;
 	private String publicationNumber;
 	//private String publishDate; //kipirs에서는 일게 되어 있으나..실제론 아래걸로 넘어옴
 	private String publicationDate;
 	private String astrtCont;
 	private String bigDrawing;
 	private String drawing;
 	private String applicantName;
	public String getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	public String getRegisterStatus() {
		return registerStatus;
	}
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
	public String getInventionTitle() {
		return inventionTitle;
	}
	public void setInventionTitle(String inventionTitle) {
		this.inventionTitle = inventionTitle;
	}
	public String getIpcNumber() {
		return ipcNumber;
	}
	public void setIpcNumber(String ipcNumber) {
		this.ipcNumber = ipcNumber;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getOpenNumber() {
		return openNumber;
	}
	public void setOpenNumber(String openNumber) {
		this.openNumber = openNumber;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getPublicationNumber() {
		return publicationNumber;
	}
	public void setPublicationNumber(String publicationNumber) {
		this.publicationNumber = publicationNumber;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getAstrtCont() {
		return astrtCont;
	}
	public void setAstrtCont(String astrtCont) {
		this.astrtCont = astrtCont;
	}
	public String getBigDrawing() {
		return bigDrawing;
	}
	public void setBigDrawing(String bigDrawing) {
		this.bigDrawing = bigDrawing;
	}
	public String getDrawing() {
		return drawing;
	}
	public void setDrawing(String drawing) {
		this.drawing = drawing;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}


}
