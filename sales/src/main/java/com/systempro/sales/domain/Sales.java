package com.systempro.sales.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.systempro.sales.domain.data.SalesVO;
import com.systempro.sales.enums.Status;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sales implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime instante;
	@OneToMany(mappedBy = "id.sales", fetch = FetchType.LAZY)
	private Set<ItemSales> itens = new HashSet<>();

	private String rastreio;
	private Integer status;
	@ElementCollection
	@CollectionTable(name = "status_venda")
	private Set<Status> statusVenda = new HashSet<>();

	public static Sales create(SalesVO sales) {
		sales.setInstante(LocalDateTime.now());
		sales.getStatusVenda().add(Status.toEnum(sales.getStatus()));
		return new ModelMapper().map(sales, Sales.class);
	}

	public Sales() {

	}

	public Sales(Long id, LocalDateTime instante, String rastreio, Status status) {
		this.id = id;
		this.instante = instante;
		this.rastreio = rastreio;
		this.status = (status == null) ? null : status.getCod();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public void setInstante(LocalDateTime instante) {
		this.instante = instante;
	}

	public Set<ItemSales> getItens() {
		return itens;
	}

	public void setItens(Set<ItemSales> itens) {
		this.itens = itens;
	}

	public String getRastreio() {
		return rastreio;

	}

	public void setRastreio(String rastreio) {
		this.rastreio = rastreio;
	}

	public Double getValorTotal() {
		double valor = 0;
		for (ItemSales p : itens) {
			valor = valor + p.getSubTotal();
		}
		return valor;
	}

	public Set<Status> getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(Set<Status> statusVenda) {
		this.statusVenda = statusVenda;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		Sales other = (Sales) obj;
		return Objects.equals(id, other.id);
	}

}
