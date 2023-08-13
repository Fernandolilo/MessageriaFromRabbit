package com.systempro.sales.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systempro.sales.domain.Product;
import com.systempro.sales.repositories.ProductRepository;

@Service
public class ProductService {

	
	private final ProductRepository repository;

	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}
	
	public List<Product> findByName(String name) {
		List<Product> prod = repository.findByNameLike(name);
		return prod;
	}

	
}
