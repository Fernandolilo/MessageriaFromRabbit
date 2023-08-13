package com.systempro.sales.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systempro.sales.domain.Sales;
import com.systempro.sales.domain.data.SalesVO;
import com.systempro.sales.service.SalesService;

@RestController
@RequestMapping(value = "/pedidos")
public class SalesController {

	private final SalesService service;

	public SalesController(SalesService service) {
		this.service = service;
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/yaml" }, consumes = {
			"application/json", "application/xml", "application/yaml" })
	public SalesVO create(@RequestBody SalesVO sla) {
		SalesVO sales = service.create(sla);
		sales.add(linkTo(methodOn(SalesController.class).findById(sales.getId())).withSelfRel());
		return sales;
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/yaml" })
	public SalesVO findById(@PathVariable("id") Long id) {
		SalesVO sales = service.findById(id);
		sales.add(linkTo(methodOn(SalesController.class).findById(id)).withSelfRel());
		return sales;
	}

	@GetMapping(value = "/search", produces = { "application/json"})
	public ResponseEntity<List<Sales>> findByPeriod(
			@RequestParam LocalDateTime instante,
			@RequestParam LocalDateTime termino) {
		List<Sales> collection = service.findBySalesDate(instante, termino);
		return ResponseEntity.ok().body(collection);
	}

}
