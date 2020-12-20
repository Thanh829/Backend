package com.tlcn.thebeats.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ImageFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String url;
	private long songOwnerId;
	private long artistOwnerId;
	
	
	public long getSongOwnerId() {
		return songOwnerId;
	}
	public void setSongOwnerId(long songOwnerId) {
		this.songOwnerId = songOwnerId;
	}
	public long getArtistOwnerId() {
		return artistOwnerId;
	}
	public void setArtistOwnerId(long artistOwnerId) {
		this.artistOwnerId = artistOwnerId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public ImageFile(String url, long songOwnerId, long artistOwnerId) {
		super();
		this.url = url;
		this.songOwnerId=songOwnerId;
		this.artistOwnerId = artistOwnerId;
	}
	
	
	public ImageFile() {};
	
	
	
}
