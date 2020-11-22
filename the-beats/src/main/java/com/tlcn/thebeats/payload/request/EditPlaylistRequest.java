package com.tlcn.thebeats.payload.request;

public class EditPlaylistRequest {
	
	private int playlistId;
	private String name;
	public int getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EditPlaylistRequest(int playlistId, String name) {
		super();
		this.playlistId = playlistId;
		this.name = name;
	}
	public EditPlaylistRequest() {};

}
