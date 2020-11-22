package com.tlcn.thebeats.payload.response;

public class URLResponse {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public URLResponse(String url) {
		super();
		this.url = url;
	}
	
	public URLResponse() {}
}
