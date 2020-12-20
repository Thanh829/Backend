package com.tlcn.thebeats.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Artist{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double payslip;
	private String paypalAccount;
	
	private String avatar;
	private String coverImageId;
	private String artistName;
	private boolean isActive;
	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@OneToMany(mappedBy = "artist",fetch = FetchType.LAZY)
	private List<Song> songs;
	
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	private long userId;
	public double getPayslip() {
		return payslip;
	}
	public void setPayslip(double payslip) {
		this.payslip = payslip;
	}
	public String getPaypalAccount() {
		return paypalAccount;
	}
	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Artist( String paypalAccount) {
		super();
		this.paypalAccount = paypalAccount;
	}
	
	public Artist() {}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getCoverImageId() {
		return coverImageId;
	}
	public void setCoverImageId(String coverImageId) {
		this.coverImageId = coverImageId;
	}
	
	
	
	
	
	
}
