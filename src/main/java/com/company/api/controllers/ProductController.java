package com.company.api.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.api.models.Product;
import com.company.api.pojo.Response;
import com.company.api.services.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<?> getProducts() {
		try {
			List<Product> products = this.productService.findAll();
			return new ResponseEntity<>(new Response(true, "success", products),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
		try {
			Product product = this.productService.findById(id);
			if( product == null ) {
				return new ResponseEntity<>(new Response(false, "id: "+id+" not found", null),HttpStatus.OK);
			}
			List<Product> productArr = Arrays.asList(product);
			return new ResponseEntity<>(new Response(true, "success", productArr),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> createProduct(@RequestBody Product product) {
		try {
			Product productCreated = this.productService.save(product);
			return new ResponseEntity<>(new Response(true, "created", productCreated),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id, @RequestBody Product product) {
		try {
			Product dbProduct = this.productService.findById(id);
			if( dbProduct == null ) {
				return new ResponseEntity<>(new Response(false, "id: "+id+" not found", null),HttpStatus.OK);
			}
			dbProduct = dbProduct.updatetProduct(product);
			dbProduct.setId(id);
			
			var productUpdated = this.productService.save(dbProduct);
			var productArr = Arrays.asList(productUpdated);
			return new ResponseEntity<>(new Response(true, "updated", productArr),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id) {
		try {
			Product dbProduct = this.productService.findById(id);
			if( dbProduct == null ) {
				return new ResponseEntity<>(new Response(false, "id: "+id+" not found", null),HttpStatus.OK);
			}
			this.productService.deleteById(id);
			List<Product> productDeleted = Arrays.asList(dbProduct);
			return new ResponseEntity<>(new Response(true, "deleted", productDeleted),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
