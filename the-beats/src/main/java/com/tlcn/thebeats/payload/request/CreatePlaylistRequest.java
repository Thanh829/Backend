package com.tlcn.thebeats.payload.request;

public class CreatePlaylistRequest {

	private String name;
	private long userId;
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreatePlaylistRequest(String name, long userId) {
		super();
		this.name = name;
		this.userId= userId;
	}
	
	
	
}
