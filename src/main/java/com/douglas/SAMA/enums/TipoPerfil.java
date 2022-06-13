package com.douglas.SAMA.enums;

public enum TipoPerfil {
	ADMIN(1, "ROLE_ADMIN"), USER(2, "ROLE_USER"), OPERATOR(3, "ROLE_OPERATOR");

	private long cod;
	private String descricao;

	private TipoPerfil(long cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public long getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoPerfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoPerfil x : TipoPerfil.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}

		}
		throw new IllegalArgumentException("Perfil inválido");
	}
}
