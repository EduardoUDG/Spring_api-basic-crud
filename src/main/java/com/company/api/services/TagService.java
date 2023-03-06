package com.company.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.api.models.Tag;
import com.company.api.repositories.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;

	@Transactional(readOnly = true)
	public List<Tag> findAll() {
		return this.tagRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Tag findById(Long id) {
		return this.tagRepository.findById(id);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.tagRepository.deleteById(id);
	}
	
	@Transactional
	public Tag save(Tag tag) {
		return this.tagRepository.save(tag);
	}

}
