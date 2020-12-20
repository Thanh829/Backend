package com.tlcn.thebeats.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class EditProfileRequest {
	
	private long userId;
	private int artistId;
	private MultipartFile avatar;
	private MultipartFile coverImage;
	private String artistName;
	private String paypalAccount;
	public MultipartFile getAvatar() {
		return avatar;
	}
	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}
	public MultipartFile getCoverImage() {
		return coverImage;
	}
	public void setCoverImage(MultipartFile coverImage) {
		this.coverImage = coverImage;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getPaypalAccount() {
		return paypalAccount;
	}
	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
	public EditProfileRequest(MultipartFile avatar, MultipartFile coverImage, String artistName, String paypalAccount) {
		super();
		this.avatar = avatar;
		this.coverImage = coverImage;
		this.artistName = artistName;
		this.paypalAccount = paypalAccount;
	}
	
	
	public EditProfileRequest(long userId, int artistId, MultipartFile avatar, MultipartFile coverImage,
			String artistName, String paypalAccount) {
		super();
		this.userId = userId;
		this.artistId = artistId;
		this.avatar = avatar;
		this.coverImage = coverImage;
		this.artistName = artistName;
		this.paypalAccount = paypalAccount;
	}
	public EditProfileRequest() {};

}
