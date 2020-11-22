package com.tlcn.thebeats.payload.request;

import java.util.List;

import com.tlcn.thebeats.models.Song;

public class AddSongToPlaylistRequest {
	
	private int playlistId;
	private Song song;
	public int getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public AddSongToPlaylistRequest(int playlistId, Song song) {
		super();
		this.playlistId = playlistId;
		this.song = song;
	}
	
	public AddSongToPlaylistRequest() {};
	

}
