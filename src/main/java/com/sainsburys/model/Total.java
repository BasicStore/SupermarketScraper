package com.sainsburys.model;

public class Total {

	public static final String CURRENCY = "£";
	public static final String VAT = "0.83";
	
	private String gross;
	private String vat;
	
	public Total(String gross, String vat) {
		this.gross = gross;
		this.vat = vat;
	}
	
	public String getGross() {
		return gross;
	}

	public String getVat() {
		return vat + "%";
	}			
	
}
