package com.systempro.product.domain.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.systempro.product.domain.Category;
import com.systempro.product.domain.Product;

public class CategoryVO extends RepresentationModel<CategoryVO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String category;
	private List<Product> products = new ArrayList<>();

	public CategoryVO() {

	}

	public static CategoryVO create(Category category) {
		return new ModelMapper().map(category, CategoryVO.class);
	}

	public CategoryVO(Long id, String category) {
		this.id = id;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
