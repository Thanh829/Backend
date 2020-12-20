package com.tlcn.thebeats.payload.request;

public class IsSongPayedRequest {
	
	private long userId;
	private long songId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getSongId() {
		return songId;
	}
	public void setSongId(long songId) {
		this.songId = songId;
	}
	public IsSongPayedRequest(long userId, long songId) {
		super();
		this.userId = userId;
		this.songId = songId;
	}
	
	public IsSongPayedRequest() {};

}
