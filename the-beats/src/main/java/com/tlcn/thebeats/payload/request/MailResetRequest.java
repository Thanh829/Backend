package com.tlcn.thebeats.payload.request;

public class MailResetRequest {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MailResetRequest(String email) {
		super();
		this.email = email;
	}
	
	public MailResetRequest() {}
}
