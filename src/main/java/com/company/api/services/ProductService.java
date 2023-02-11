package com.company.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.api.models.Product;
import com.company.api.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return this.productRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return this.productRepository.findById(id);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.productRepository.deleteById(id);
	}
	
	@Transactional
	public Product save(Product product) {
		return this.productRepository.save(product);
	}
	
}
