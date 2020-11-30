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
	private long songOrArtistOwnerId;
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
	public long getSongOrArtistOwnerId() {
		return songOrArtistOwnerId;
	}
	public void setSongOrArtistOwnerId(long songOrArtistOwnerId) {
		this.songOrArtistOwnerId = songOrArtistOwnerId;
	}
	public ImageFile( String url, long songOrArtistOwnerId) {
		super();
		this.url = url;
		this.songOrArtistOwnerId = songOrArtistOwnerId;
	}
	
	public ImageFile() {};
	
	
	
}
