package com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models;

import java.util.UUID;

public class PessoaDTOView extends PessoaDTOBase {

	private UUID id;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	
}
