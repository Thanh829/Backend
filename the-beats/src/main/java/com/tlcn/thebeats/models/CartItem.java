package com.tlcn.thebeats.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date dateAdded;
	private int userId;
	@Column(nullable = true)
	private int orderId;
	private double price;
	private int songId;
	private int artistId;
	private String songName;
	private String avatar;
	
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public CartItem(Date dateAdded, int userId, double price, int songId, String songName) {
		super();
		this.dateAdded = dateAdded;
		this.userId = userId;
		
		this.price = price;
		this.songId = songId;
		this.songName = songName;
	}
	
	
	
	public CartItem(Date dateAdded, int userId, double price, int songId, int artistId, String songName) {
		super();
		this.dateAdded = dateAdded;
		this.userId = userId;
		this.price = price;
		this.songId = songId;
		this.artistId = artistId;
		this.songName = songName;
	}
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
	private CartItem() {};
	
	
	
	

}
