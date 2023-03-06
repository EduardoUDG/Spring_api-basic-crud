package com.company.api.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.company.api.models.Tag;

public interface TagRepository extends CrudRepository<Tag, Serializable> {

	public List<Tag> findAll();
	
	public Tag findById(Long id);
		
	public void deleteById(Long id);
	
	@SuppressWarnings("unchecked")
	public Tag save(Tag tag);
	
}
