package com.tlcn.thebeats.payload.request;

import java.util.Date;

public class CreateInvoiceRequest {
	private int userId;
	private String userName;
	private String orderDate;
	private String payeeEmail;
	private String paymentId;
	private double totalCost;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getPayeeEmail() {
		return payeeEmail;
	}
	public void setPayeeEmail(String payeeEmail) {
		this.payeeEmail = payeeEmail;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	
	public CreateInvoiceRequest(int userId, String userName, String orderDate, String payeeEmail, String paymentId,
			double totalCost) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.orderDate = orderDate;
		this.payeeEmail = payeeEmail;
		this.paymentId = paymentId;
		this.totalCost = totalCost;
	}
	public CreateInvoiceRequest() {}
	
}
