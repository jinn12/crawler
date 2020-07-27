package com.cmn.cmn.model;

public enum Constants {
	YN_Y("Y")
	, YN_N("N")
	, TOP_BOTTOM_TOP("T")
	, TOP_BOTTOM_BOTTOM("B")
	, SORT_ASC("ASC")
	, SORT_DESC("DESC")
	, TF_T("T")
	, TF_F("F")
	, TRUE("true")
	, FALSE("false");

	private String value;

    private Constants(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}
}
