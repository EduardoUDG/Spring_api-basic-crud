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
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
	@Size(min = 2, message = ValidationMessages.LENGTH_GREATER_THAN_2)
	private String name;
    
	@NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @Size(min = 2, message = ValidationMessages.LENGTH_GREATER_THAN_2)
	private String description;
	
	@NotNull(message = ValidationMessages.NOT_NULL_MESSAGE)
    @Column
	private Double price;
	
	@Column
	private Boolean status = true;
	
	public Product updatetProduct( Product product ) {
		var newProduct = new Product();
		newProduct.setDescription(product.getDescription());
		newProduct.setName(product.getName());
		newProduct.setPrice(product.getPrice());
		newProduct.setStatus(product.getStatus());
		return newProduct; 
	}
	
}
