package com.company.api.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.api.models.Tag;
import com.company.api.pojo.Response;
import com.company.api.services.TagService;
import com.company.api.services.ValidationService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api")
public class TagController {

	@Autowired
	private TagService tagService;
	
	@Autowired
	private ValidationService validationService;
	
	@GetMapping("/tags")
	public ResponseEntity<?> getTags() {
		try {
			List<Tag> tags = this.tagService.findAll();
			if(tags.isEmpty() ) return this.validationService.emptyDataResponse();
			
			return new ResponseEntity<>(new Response(true, "success", tags),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tags/{id}")
	public ResponseEntity<?> getTagById(@PathVariable("id") Long id) {
		try {
			Tag dbTag = this.tagService.findById(id);
			if( dbTag == null ) return this.validationService.idNotFoundResponse(id);
			
			List<Tag> tagArr = Arrays.asList(dbTag);
			return new ResponseEntity<>(new Response(true, "success", tagArr),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/tags")
	public ResponseEntity<?> createTag(@Valid @RequestBody Tag tag, BindingResult result) {
        if (result.hasErrors()) return this.validationService.bodyErrorResponse(result);
		try {
			Tag tagCreated = this.tagService.save(tag);
			return new ResponseEntity<>(new Response(true, "created", tagCreated),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/tags/{id}")
	public ResponseEntity<?> getTagById(@PathVariable("id") Long id,@Valid @RequestBody Tag tag, BindingResult result) {
		if (result.hasErrors()) return this.validationService.bodyErrorResponse(result);
		if ( !id.equals(tag.getId()) ) return this.validationService.idIsNotEqualBodyResponse();
		try {
			Tag dbTag = this.tagService.findById(id);
			if( dbTag == null ) return this.validationService.idNotFoundResponse(id);
			
			dbTag = dbTag.updateTag(tag);
			dbTag.setId(id);
			
			var tagUpdated = this.tagService.save(dbTag);
			var tagArr = Arrays.asList(tagUpdated);
			return new ResponseEntity<>(new Response(true, "updated", tagArr),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/tags/{id}")
	public ResponseEntity<?> deleteTagById(@PathVariable("id") Long id) {
		try {
			Tag dbTag = this.tagService.findById(id);
			if( dbTag == null ) return this.validationService.idNotFoundResponse(id);
			
			this.tagService.deleteById(id);
			List<Tag> tagDeleted = Arrays.asList(dbTag);
			return new ResponseEntity<>(new Response(true, "deleted", tagDeleted),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
