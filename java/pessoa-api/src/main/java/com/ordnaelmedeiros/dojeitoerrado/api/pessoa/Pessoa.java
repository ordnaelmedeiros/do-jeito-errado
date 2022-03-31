package com.ordnaelmedeiros.dojeitoerrado.api.pessoa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity @Table(name = "pessoa")
public class Pessoa {

	@Id
	private UUID id;
	private String nome;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@PrePersist
	void prePersist() {
		id = UUID.randomUUID();
	}
	
}
