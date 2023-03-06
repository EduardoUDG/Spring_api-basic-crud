package com.company.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.company.api.helpers.ValidationMessages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
	@Size(min = 2, message = ValidationMessages.LENGTH_GREATER_THAN_2)
	private String name;
	
	@Column
	private Boolean status = true;
	
	public Tag updateTag( Tag tag ) {
		var newTag = new Tag();
		newTag.setName(tag.getName());
		newTag.setStatus(tag.getStatus());
		return newTag; 
	}
	
}
