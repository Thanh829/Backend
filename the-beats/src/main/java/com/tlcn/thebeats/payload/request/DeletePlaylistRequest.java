package com.tlcn.thebeats.payload.request;

public class DeletePlaylistRequest {
	
	private int playlistId;

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public DeletePlaylistRequest(int playlistId) {
		super();
		this.playlistId = playlistId;
	}
	public DeletePlaylistRequest() {};

}
