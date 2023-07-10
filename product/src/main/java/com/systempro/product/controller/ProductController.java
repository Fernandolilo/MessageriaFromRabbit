package com.systempro.product.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systempro.product.domain.data.ProductVO;
import com.systempro.product.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	private final ProductService service;
	private final PagedResourcesAssembler<ProductVO> assembler;

	public ProductController(ProductService service, PagedResourcesAssembler<ProductVO> assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@GetMapping(value = "/page")
	public ResponseEntity<?> findAllPage(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "24") int limit,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Direction sortDirection = "desc".equals(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));
		Page<ProductVO> category = service.findAllPage(pageable);
		category.stream()
				.forEach(t -> t.add(linkTo(methodOn(ProductController.class).findById(t.getId())).withSelfRel()));
		PagedModel<EntityModel<ProductVO>> pageModel = assembler.toModel(category);
		return new ResponseEntity<>(pageModel, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/yaml" })
	public ProductVO findById(@PathVariable("id") Long id) {
		ProductVO category = service.findById(id);
		category.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
		return category;
	}
	
	@PostMapping(produces = {"application/json", "application/xml", "application/yaml"},
			consumes = {"application/json", "application/xml", "application/yaml"})
	public ProductVO create(@RequestBody ProductVO ProductVO) {
		ProductVO category = service.create(ProductVO);
		category.add(linkTo(methodOn(ProductController.class).findById(category.getId())).withSelfRel());
		return category;
	}
	
	@DeleteMapping()
	public ResponseEntity<Void> delete(@RequestBody ProductVO category) {
		service.delete(category);
		return ResponseEntity.noContent().build();
	}
	

	@PutMapping(produces = {"application/json", "application/xml", "application/yaml"},
			consumes = {"application/json", "application/xml", "application/yaml"})
	public ProductVO update(@RequestBody ProductVO productVO) {
		
		ProductVO cat = service.update(productVO);
		cat.add(linkTo(methodOn(ProductController.class).findById(cat.getId())).withSelfRel());
		return cat;
	}
}
