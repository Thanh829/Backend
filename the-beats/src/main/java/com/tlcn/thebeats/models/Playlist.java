package com.tlcn.thebeats.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Playlist {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int id;
		private String name;
		
		
		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(	name = "playlist_song", 
					joinColumns = @JoinColumn(name = "playlist_id"), 
					inverseJoinColumns = @JoinColumn(name = "song_id"))
		private List<Song> listSong;
		
		@ManyToOne(fetch = FetchType.EAGER, optional = false)
		@JoinColumn(name = "user_id", nullable = false)
		private User user;

		

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Song> getListSong() {
			return listSong;
		}

		public void setListSong(List<Song> listSong) {
			this.listSong = listSong;
		}
		
		

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Playlist(String name) {
			super();
			this.name = name;
		}
		
		public Playlist() {};
		
		
		

}
