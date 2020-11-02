package com.tlcn.thebeats.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tlcn.thebeats.models.Tag;
import com.tlcn.thebeats.repository.TagRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/tag")
public class TagController {
	
	@Autowired
	private TagRepository tagRepo;
	
	@PostMapping("/add")
	public Tag addTag(@RequestParam String title )
	{
		return tagRepo.save(new Tag(title));
	}
	
	@GetMapping("/all")
	public List<Tag> getALL()
	{
		return tagRepo.findAll();
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteTag(@PathVariable int id)
	{
		tagRepo.deleteById(id);
	}

}
