package com.systempro.sales.domain.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systempro.sales.domain.Category;
import com.systempro.sales.domain.ItemSales;
import com.systempro.sales.domain.Product;

public class ProductVO extends RepresentationModel<CategoryVO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Double price;
	private Integer amount;
	private Category category;

	@JsonIgnore
	private Set<ItemSales> itens = new HashSet<>();

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

	@JsonIgnore
	public Set<ItemSales> getItens() {
		return itens;
	}

	public void setItens(Set<ItemSales> itens) {
		this.itens = itens;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductVO other = (ProductVO) obj;
		return Objects.equals(id, other.id);
	}

}
