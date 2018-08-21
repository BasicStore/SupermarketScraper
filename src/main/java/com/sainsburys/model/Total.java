package com.sainsburys.model;

public class Total {

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
		return vat;
	}			
	
}
