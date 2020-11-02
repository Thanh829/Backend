package com.tlcn.thebeats.payload.request;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class addSongRequest {
	private String title;
	private Set<String> tags;
	private MultipartFile song;
	
	public Set<String> getTags() {
		return tags;
	}
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	public MultipartFile getSong() {
		return song;
	}
	public void setSong(MultipartFile song) {
		this.song = song;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
