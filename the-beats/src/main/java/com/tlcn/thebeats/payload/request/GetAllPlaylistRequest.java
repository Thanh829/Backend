package com.tlcn.thebeats.payload.request;

public class GetAllPlaylistRequest {
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public GetAllPlaylistRequest(long userId) {
		super();
		this.userId = userId;
	}
	
	

}
