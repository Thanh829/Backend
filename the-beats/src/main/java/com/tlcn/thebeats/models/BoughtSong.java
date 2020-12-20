package com.tlcn.thebeats.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BoughtSong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long buyerId;
	private long artistId;
	private long songId;
	
	private boolean isPayForArist=false;
	
	public boolean isPayForArist() {
		return isPayForArist;
	}
	public void setPayForArist(boolean isPayForArist) {
		this.isPayForArist = isPayForArist;
	}
	public long getSongId() {
		return songId;
	}
	public void setSongId(long songId) {
		this.songId = songId;
	}

	private long date;
	private double price;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public long getArtistId() {
		return artistId;
	}
	public void setArtistId(long artistId) {
		this.artistId = artistId;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public BoughtSong(long buyerId, long artistId, long date, double price) {
		super();
		this.buyerId = buyerId;
		this.artistId = artistId;
		this.date = date;
		this.price = price;
	}
	
	public BoughtSong() {}
	public BoughtSong(long buyerId, long artistId, long songId, long date, double price) {
		super();
		this.buyerId = buyerId;
		this.artistId = artistId;
		this.songId = songId;
		this.date = date;
		this.price = price;
	}
	public BoughtSong(long buyerId, long artistId, long songId, boolean isPayForArist, long date, double price) {
		super();
		this.buyerId = buyerId;
		this.artistId = artistId;
		this.songId = songId;
		this.isPayForArist = isPayForArist;
		this.date = date;
		this.price = price;
	};
	
	
	
	
}
