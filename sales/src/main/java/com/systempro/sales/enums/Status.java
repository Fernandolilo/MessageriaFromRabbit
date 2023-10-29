package com.systempro.sales.enums;

public enum Status {
	
	RECEBEMOS_SEU_PEDIDO(1, "RECEBEMOS_SEU_PEDIDO"),
	PEDIDO_EM_SEPARACAO(2, "PEDIDO_EM_SEPARACAO"),
	PEDIDO_ENVIADO(3, "PEDIDO_ENVIADO");

	private Integer cod;
	private String descricao;

	private Status(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Status toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Status x : Status.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
