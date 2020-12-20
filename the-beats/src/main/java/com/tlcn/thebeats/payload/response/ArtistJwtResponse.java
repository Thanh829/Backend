package com.tlcn.thebeats.payload.response;

import java.util.Date;
import java.util.List;

public class ArtistJwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private long timeExpire;
	private List<String> roles;
	private String paypalAccount;
	private int artistId;
	private String avatar;
	private String coverImage;
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaypalAccount() {
		return paypalAccount;
	}

	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public ArtistJwtResponse(String token, long id, String username, String email, List<String> roles, long timeExpire,
			String paypalAccount, int artistId, String avatar, String coverImage) {
		super();
		this.token = token;

		this.id = id;
		this.username = username;
		this.email = email;
		this.timeExpire = timeExpire;
		this.roles = roles;
		this.paypalAccount = paypalAccount;
		this.artistId = artistId;
		this.avatar = avatar;
		this.coverImage = coverImage;
	}

	public ArtistJwtResponse(String token, long id, String username, String email, List<String> roles, long timeExpire,
			String paypalAccount, int artistId, String avatar, String coverImage, boolean active) {
		super();
		this.token = token;

		this.id = id;
		this.username = username;
		this.email = email;
		this.timeExpire = timeExpire;
		this.roles = roles;
		this.paypalAccount = paypalAccount;
		this.artistId = artistId;
		this.avatar = avatar;
		this.coverImage = coverImage;
		this.active = active;
	}

	public long getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(long timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

}
