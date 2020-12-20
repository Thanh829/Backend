package com.tlcn.thebeats.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ArtistInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int artistId;
	@JsonIgnore
	@OneToMany(mappedBy = "invoice", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ArtistInvoiceItem> items;
	private double subtotal;
	private double total;
	
	private String payer;
	private String payee;
	private String date;
	
	
	
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
	public ArtistInvoice(int artistId, double subtotal, double total) {
		super();
		this.artistId = artistId;
		this.subtotal = subtotal;
		this.total = total;
	}
	public ArtistInvoice(int artistId,  double subtotal, double total, String payer,
			String payee, String date) {
		super();
		this.artistId = artistId;
		this.subtotal = subtotal;
		this.total = total;
		this.payer = payer;
		this.payee = payee;
		this.date = date;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArtistInvoice() {}
	
	
	
	
	
	
	
}
