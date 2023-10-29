package com.systempro.sales.domain.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.systempro.sales.domain.ItemSales;
import com.systempro.sales.domain.Sales;
import com.systempro.sales.enums.Status;

public class SalesVO extends RepresentationModel<SalesVO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime instante;

	private String rastreio;

	private Set<ItemSales> itens = new HashSet<>();

	private Integer status;
	private Set<Status> statusVenda = new HashSet<>();

	public static SalesVO create(Sales sales) {
		sales.setInstante(LocalDateTime.now());
		sales.getStatusVenda().add(Status.toEnum(sales.getStatus()));
		return new ModelMapper().map(sales, SalesVO.class);
	}

	public SalesVO() {

	}

	public SalesVO(Long id, LocalDateTime instante, String rastreio, Status cod) {
		this.id = id;
		this.instante = instante;
		this.rastreio = rastreio;
		this.status = (cod == null) ? null : cod.getCod();
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

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public void setInstante(LocalDateTime instante) {
		this.instante = instante;
	}

	public Set<ItemSales> getItens() {
		return itens;
	}

	public void setItens(Set<ItemSales> itens) {
		this.itens = itens;
	}

	public Double getValorTotal() {
		double valor = 0;
		for (ItemSales p : itens) {
			valor = p.getAmount() * p.getPrice();
		}
		return valor;
	}

	public String getRastreio() {
		if (!getStatus().equals(Status.RECEBEMOS_SEU_PEDIDO)) {
			return rastreio;
		}
		return "aguarde";

	}

	public void setRastreio(String rastreio) {
		this.rastreio = rastreio;
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
		SalesVO other = (SalesVO) obj;
		return Objects.equals(id, other.id);
	}

}
