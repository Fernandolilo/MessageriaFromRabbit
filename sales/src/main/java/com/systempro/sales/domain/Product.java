package com.systempro.sales.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systempro.sales.domain.data.ProductVO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Double price;

	private Integer amount;
	

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonBackReference
	@OneToMany(mappedBy = "id.product")
	private Set<ItemSales> itens = new HashSet<>();

	public Product() {
	}

	public Product(Long id, String name, Double price, Integer amount, Category category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.category = category;
	}

	public static Product create(ProductVO product) {
		return new ModelMapper().map(product, Product.class);
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

	public void setCategory(Category category) {
		this.category = category;
	}

	@JsonIgnore
	public Set<ItemSales> getItens() {
		return itens;
	}

	public void setItens(Set<ItemSales> itens) {
		this.itens = itens;
	}

	@JsonIgnore
	public List<Sales> getSales() {
		List<Sales> lista = new ArrayList<>();
		for (ItemSales x : itens) {
			lista.add(x.getSales());
		}
		return lista;
	}

	public void getValue(Integer amount) {
		this.amount -= amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

}
