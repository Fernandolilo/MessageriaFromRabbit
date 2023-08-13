package com.systempro.sales.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class ItemSales implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId // ID embutido em tipo auxiliar
	private ItemSalesPK id = new ItemSalesPK();

	private String name;
	private Integer amount;
	private Double price;

	public ItemSales() {

	}

	@JsonIgnore
	public ItemSales(Sales sales, Product product, String name, Integer amount, Double price) {
		id.setSales(sales);
		id.setProduct(product);
		this.name = name;
		this.amount = amount;
		this.price = price;
	}

	public double getSubTotal() {
		return price * amount;
	}

	@JsonIgnore
	public Sales getSales() {
		return id.getSales();
	}

	public void setSales(Sales sales) {
		id.setSales(sales);
	}

	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		id.setProduct(product);
	}

	public ItemSalesPK getId() {
		return id;
	}

	public void setId(ItemSalesPK id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Integer getValue() {
		return getProduct().getAmount();
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
		ItemSales other = (ItemSales) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(getProduct().getName());
		builder.append(", Qte: ");
		builder.append(getAmount());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(getPrice()));
		builder.append(", SubTotal: ");
		builder.append(nf.format(getSubTotal()));
		builder.append("\n");

		return builder.toString();
	}

}
