package com.tlcn.thebeats.payment;

import java.util.List;

import com.tlcn.thebeats.models.CartItem;

public class Order {

	
	private List<CartItem> items;
	private double total;
	private String currency;
	private String method;
	private String intent;
	private String description;
	
	
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	
	public Order(List<CartItem> items, double total, String currency, String method, String intent,
			String description) {
		super();
		this.items = items;
		this.total = total;
		this.currency = currency;
		this.method = method;
		this.intent = intent;
		this.description = description;
	}
	
	

}
