package com.tlcn.thebeats.payload.request;

import com.tlcn.thebeats.models.ArtistInvoice;

public class ArtistInvoiceItem {

	private double price;
	private int quaranty;
	private double total;
	private String title;
	private ArtistInvoice invoice;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuaranty() {
		return quaranty;
	}
	public void setQuaranty(int quaranty) {
		this.quaranty = quaranty;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArtistInvoice getInvoice() {
		return invoice;
	}
	public void setInvoice(ArtistInvoice invoice) {
		this.invoice = invoice;
	}
	public ArtistInvoiceItem(double price, int quaranty, double total, String title, ArtistInvoice invoice) {
		super();
		this.price = price;
		this.quaranty = quaranty;
		this.total = total;
		this.title = title;
		this.invoice = invoice;
	}
	
	
	
	
	
	
	
}
