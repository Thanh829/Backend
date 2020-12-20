package com.tlcn.thebeats.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ArtistInvoiceItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double price;
	private int checkoutTimes;
	private double total;
	private String songName;
	
	@ManyToOne()
	private ArtistInvoice invoice;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getCheckoutTimes() {
		return checkoutTimes;
	}
	public void setCheckoutTimes(int checkoutTimes) {
		this.checkoutTimes = checkoutTimes;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public ArtistInvoice getInvoice() {
		return invoice;
	}
	public void setInvoice(ArtistInvoice invoice) {
		this.invoice = invoice;
	}
	
	
	public ArtistInvoiceItem(double price, int checkoutTimes, double total, String songName, ArtistInvoice invoice) {
		super();
		this.price = price;
		this.checkoutTimes = checkoutTimes;
		this.total = total;
		this.songName = songName;
		this.invoice = invoice;
	}
	public ArtistInvoiceItem(double price, int checkoutTimes, double total) {
		super();
		this.price = price;
		this.checkoutTimes = checkoutTimes;
		this.total = total;
	}
	
	public ArtistInvoiceItem() {}
	
	
	
	
	

}
