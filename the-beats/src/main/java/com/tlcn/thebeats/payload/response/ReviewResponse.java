package com.tlcn.thebeats.payload.response;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;

public class ReviewResponse {
	
	private PayerInfo payerInfo ;
	private Transaction transaction ;
	private ShippingAddress shippingAddress ;
	public PayerInfo getPayerInfo() {
		return payerInfo;
	}
	public void setPayerInfo(PayerInfo payerInfo) {
		this.payerInfo = payerInfo;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public ReviewResponse(PayerInfo payerInfo, Transaction transaction, ShippingAddress shippingAddress) {
		super();
		this.payerInfo = payerInfo;
		this.transaction = transaction;
		this.shippingAddress = shippingAddress;
	}
	
	public ReviewResponse() {}
	

}
