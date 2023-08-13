package com.systempro.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systempro.sales.domain.Product;
import com.systempro.sales.service.ProductService;

@RestController
@RequestMapping(value= "/products")
public class ProductController {

	private final ProductService service;
	

	public ProductController(ProductService service) {
		this.service = service;
	}


	@GetMapping(value = "/search", produces = { "application/json", "application/xml", "application/yaml" })
	public List<Product> findByName(@RequestParam(name = "name", required = true) String name){
		List<Product> list = service.findByName(name);
		return list;
	}
}
