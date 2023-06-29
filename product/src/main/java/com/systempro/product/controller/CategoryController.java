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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systempro.product.domain.data.CategoryVO;
import com.systempro.product.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	private final CategoryService service;
	private final PagedResourcesAssembler<CategoryVO> assembler;

	public CategoryController(CategoryService service, PagedResourcesAssembler<CategoryVO> assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@GetMapping(value = "/page")
	public ResponseEntity<?> findAllPage(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "24") int limit,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Direction sortDirection = "desc".equals(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));
		Page<CategoryVO> category = service.findAllPage(pageable);
		category.stream()
				.forEach(t -> t.add(linkTo(methodOn(CategoryController.class).findById(t.getId())).withSelfRel()));
		PagedModel<EntityModel<CategoryVO>> pageModel = assembler.toModel(category);
		return new ResponseEntity<>(pageModel, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/yaml" })
	public CategoryVO findById(@PathVariable("id") Long id) {
		CategoryVO category = service.findById(id);

		category.add(linkTo(methodOn(CategoryController.class).findById(id)).withSelfRel());
		return category;
	}
	
	@PostMapping(produces = { "application/json", "application/xml", "application/yaml" }, consumes = {
			"application/json", "application/xml", "application/yaml" })
	public CategoryVO create (@RequestBody CategoryVO categoryVO) {
		CategoryVO category = service.create(categoryVO);
		category.add(linkTo(methodOn(CategoryController.class).findById(category.getId())).withSelfRel());
		return category;
	}

}
