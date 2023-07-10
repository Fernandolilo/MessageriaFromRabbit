package com.systempro.product.domain.data;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.systempro.product.domain.Category;
import com.systempro.product.domain.Product;

@JsonPropertyOrder({ "id", "name", "price", "amount" , "category" })
public class ProductVO extends RepresentationModel<CategoryVO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("price")
	private Double price;
	@JsonProperty("amount")
	private Integer amount;
	@JsonProperty("category")
	private Category category;

	public ProductVO() {
	}

	public ProductVO(Long id, String name, Double price, Integer amount, Category category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.category = category;
	}

	public static ProductVO create(Product product) {
		return new ModelMapper().map(product, ProductVO.class);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategoria(Category category) {
		this.category = category;
	}

}
