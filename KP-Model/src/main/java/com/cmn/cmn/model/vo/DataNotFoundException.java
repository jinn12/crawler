package com.cmn.cmn.model.vo;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3046272342297675790L;

	private String model;

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String model) {
		this.model = model;
	}

	@Override
	public String getMessage() {
		return this.model + " not found";
	}

}
