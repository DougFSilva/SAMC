package com.douglas.SAMA.DTO;

import javax.validation.constraints.NotBlank;

public class AulaFORM {

	@NotBlank(message = "O campo aula é requerido")
	private String aula;

	public AulaFORM() {
		super();
	}

	public AulaFORM(String aula) {
		super();
		this.aula = aula;
	}

	public String getAula() {
		return aula;
	}

}
