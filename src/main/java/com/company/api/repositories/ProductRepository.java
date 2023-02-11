package com.company.api.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.company.api.models.Product;

public interface ProductRepository  extends CrudRepository<Product, Serializable>{
	
	public List<Product> findAll();
	
	public Product findById(Long id);
		
	public void deleteById(Long id);
	
	@SuppressWarnings("unchecked")
	public Product save(Product product);
		
}
