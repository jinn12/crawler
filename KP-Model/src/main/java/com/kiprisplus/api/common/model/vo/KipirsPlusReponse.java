package com.kiprisplus.api.common.model.vo;
//사용안함.
public class KipirsPlusReponse {
	String resultCode;
	String resultMag;
	int numOfRows;
	int pageNo;
	int totalCount;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMag() {
		return resultMag;
	}
	public void setResultMag(String resultMag) {
		this.resultMag = resultMag;
	}
	public int getNumOfRows() {
		return numOfRows;
	}
	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


}
