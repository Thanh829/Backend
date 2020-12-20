package com.tlcn.thebeats.payload.request;

import java.util.List;

public class CreateArtistInvoiceRequest {
	
	private int artistId;
	private List<ArtistInvoiceItem> items;
	private double subtotal;
	private double total;
	
	private String payer;
	private String payee;
	private String date;
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
	public List<ArtistInvoiceItem> getItems() {
		return items;
	}
	public void setItems(List<ArtistInvoiceItem> items) {
		this.items = items;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public CreateArtistInvoiceRequest(int artistId, List<ArtistInvoiceItem> items, double subtotal, double total,
			String payer, String payee, String date) {
		super();
		this.artistId = artistId;
		this.items = items;
		this.subtotal = subtotal;
		this.total = total;
		this.payer = payer;
		this.payee = payee;
		this.date = date;
	}
	
	
	
	

}
