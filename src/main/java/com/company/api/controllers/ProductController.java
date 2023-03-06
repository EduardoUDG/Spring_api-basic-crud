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

import com.company.api.models.Product;
import com.company.api.pojo.Response;
import com.company.api.services.ProductService;
import com.company.api.services.ValidationService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ValidationService validationService;
	
	@GetMapping("/products")
	public ResponseEntity<?> getProducts() {
		try {
			List<Product> products = this.productService.findAll();
			if(products.isEmpty() ) return this.validationService.emptyDataResponse();
			
			return new ResponseEntity<>(new Response(true, "success", products),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
		try {
			Product dbProduct = this.productService.findById(id);
			if( dbProduct == null ) return this.validationService.idNotFoundResponse(id);
			
			List<Product> productArr = Arrays.asList(dbProduct);
			return new ResponseEntity<>(new Response(true, "success", productArr),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) return this.validationService.bodyErrorResponse(result);
		try {
			Product productCreated = this.productService.save(product);
			return new ResponseEntity<>(new Response(true, "created", productCreated),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id,@Valid @RequestBody Product product, BindingResult result) {
		if (result.hasErrors()) return this.validationService.bodyErrorResponse(result);
		if ( !id.equals(product.getId()) ) return this.validationService.idIsNotEqualBodyResponse();
		try {
			Product dbProduct = this.productService.findById(id);
			if( dbProduct == null ) return this.validationService.idNotFoundResponse(id);
			
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
			if( dbProduct == null ) return this.validationService.idNotFoundResponse(id);
			
			this.productService.deleteById(id);
			List<Product> productDeleted = Arrays.asList(dbProduct);
			return new ResponseEntity<>(new Response(true, "deleted", productDeleted),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
