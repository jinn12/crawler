package com.kiprisplus.api.patent.model.vo.cpcInfo;

import javax.xml.bind.annotation.XmlElement;

public class PatentCpcInfoVO {
	private String cooperativepatentclassificationNumber;

	private String cooperativepatentclassificationDate;

	@XmlElement(name = "CooperativepatentclassificationNumber")
	public String getCooperativepatentclassificationNumber() {
		return cooperativepatentclassificationNumber;
	}

	public void setCooperativepatentclassificationNumber(String cooperativepatentclassificationNumber) {
		this.cooperativepatentclassificationNumber = cooperativepatentclassificationNumber;
	}
	@XmlElement(name = "CooperativepatentclassificationDate")
	public String getCooperativepatentclassificationDate() {
		return cooperativepatentclassificationDate;
	}

	public void setCooperativepatentclassificationDate(String cooperativepatentclassificationDate) {
		this.cooperativepatentclassificationDate = cooperativepatentclassificationDate;
	}

}
