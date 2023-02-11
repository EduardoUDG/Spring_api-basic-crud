package com.company.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

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
	
	@Size(min = 3, message = "debe ser mayor de 3 caracteres")
	private String name;
	
	@Size(min = 3, message = "debe ser mayor de 3 caracteres")
	private String description;
	
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
