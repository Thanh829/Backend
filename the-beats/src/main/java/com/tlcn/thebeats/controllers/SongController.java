package com.tlcn.thebeats.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tlcn.thebeats.models.Song;
import com.tlcn.thebeats.models.Tag;
import com.tlcn.thebeats.payload.request.addSongRequest;
import com.tlcn.thebeats.repository.SongRepository;
import com.tlcn.thebeats.repository.TagRepository;
import com.tlcn.thebeats.security.services.AmazonS3ClientService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/songs")
public class SongController {
	@Autowired
	private AmazonS3ClientService amazonS3ClientService;
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private TagRepository tagRepo;
	
	/**
	 * @param title
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/all")
	public List<Song> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size)
	{
		Pageable paging = PageRequest.of(page, size);
		Page<Song> pageSong= songRepo.findAll(paging);
		return pageSong.getContent();
		
	}
	@PostMapping("/add")
	public Song addVideo(@RequestParam String title, @RequestParam("tags[]") Set<String> tags,
	  @RequestParam("song") MultipartFile file) throws IOException {
	    //String id = videoService.addVideo(title, file);
		String url= amazonS3ClientService.uploadFileToS3Bucket(file, true);
		Set<Tag> listTag= new HashSet<>();
		if(tags.size()!=0)
		{
			tags.forEach(tag ->{
				Optional<Tag> tagName= tagRepo.findByTitle(tag);
				if(!tagName.isPresent()) throw new RuntimeException("Error: "+tag+" is not found.");
				listTag.add(tagName.get());
			});
		}
		Song song = new Song(title, url);
		song.setTags(listTag);
		
	    return songRepo.save(song);
	    //return new String(id);
	}
	
	@PostMapping("/addsong")
	public Song addSong(@RequestBody addSongRequest request) throws IOException {
	    //String id = videoService.addVideo(title, file);
		String url= amazonS3ClientService.uploadFileToS3Bucket(request.getSong(), true);
		Set<Tag> listTag= new HashSet<>();
		Set<String> tags= request.getTags();
		if(tags.size()!=0)
		{
			tags.forEach(tag ->{
				Optional<Tag> tagName= tagRepo.findByTitle(tag);
				if(!tagName.isPresent()) throw new RuntimeException("Error: tag is not found.");
				listTag.add(tagName.get());
			});
		}
		Song song = new Song(request.getTitle(), url);
		song.setTags(listTag);
		
	    return songRepo.save(song);
	    //return new String(id);
	}
	@GetMapping("/{id}")
	public Optional<Song> getVideo(@PathVariable long id) throws Exception {
	    Optional<Song> video = songRepo.findById(id);
	
	    return video;
	}
	
	

}
