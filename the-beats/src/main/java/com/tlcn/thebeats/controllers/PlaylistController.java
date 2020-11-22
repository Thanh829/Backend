package com.tlcn.thebeats.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tlcn.thebeats.models.Playlist;
import com.tlcn.thebeats.models.Song;
import com.tlcn.thebeats.models.User;
import com.tlcn.thebeats.payload.request.AddSongToPlaylistRequest;
import com.tlcn.thebeats.payload.request.CreatePlaylistRequest;
import com.tlcn.thebeats.payload.request.DeletePlaylistRequest;
import com.tlcn.thebeats.payload.request.EditPlaylistRequest;
import com.tlcn.thebeats.payload.request.GetAllPlaylistRequest;
import com.tlcn.thebeats.repository.PlaylistRepository;
import com.tlcn.thebeats.repository.UserRepository;



@RestController
@CrossOrigin(value = "*", maxAge = 3000)
@RequestMapping("/api/v1/playlist")
public class PlaylistController {
	@Autowired
	private PlaylistRepository playlistRepository;
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/getAll")
	public List<Playlist> getAllByUser(@RequestParam long userId)
	{
		return playlistRepository.findAllByUserId(userId);
	}
	
	@PostMapping("/create")
	public Playlist createPlaylist(@RequestBody CreatePlaylistRequest createPlaylistRequest)
	{
		Optional<User> user = userRepository.findById(createPlaylistRequest.getUserId());
		
		if(!user.isPresent()) throw new RuntimeException("User not found");
		Playlist playlist= new Playlist(createPlaylistRequest.getName());
		playlist.setUser(user.get());
		return playlistRepository.save(playlist);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<Playlist> editPlaylist(@RequestBody EditPlaylistRequest editPlaylistRequest)
	{
		Optional<Playlist> playlist = playlistRepository.findById(editPlaylistRequest.getPlaylistId());
		if(!playlist.isPresent()) throw new RuntimeException("Play list not found");
		Playlist editPlaylist=playlist.get();
		editPlaylist.setName(editPlaylistRequest.getName());
		Playlist updatedPlaylist=playlistRepository.save(editPlaylist);
		return ResponseEntity.ok(updatedPlaylist);
	}
	
	@PutMapping("/addtoplaylist")
	public Playlist addSongTopPlaylist(@RequestBody AddSongToPlaylistRequest addSongToPlaylistRequest)
	{
		Optional<Playlist> playlist = playlistRepository.findById(addSongToPlaylistRequest.getPlaylistId());
		if(!playlist.isPresent()) throw new RuntimeException("Play list not found");
		Playlist editPlaylist=playlist.get();
		List<Song> list = editPlaylist.getListSong();
		list.add(addSongToPlaylistRequest.getSong());
		editPlaylist.setListSong(list);
		return playlistRepository.save(editPlaylist);
	}
	
	@DeleteMapping("/delete")
	public void deletePlaylist(@RequestBody DeletePlaylistRequest deletePlaylistRequest)
	{
		playlistRepository.deleteById(deletePlaylistRequest.getPlaylistId());
	}
	
	
	
	
	

}
