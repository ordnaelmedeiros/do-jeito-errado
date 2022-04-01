package com.ordnaelmedeiros.dojeitoerrado.api.pessoa;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ordnaelmedeiros.dojeitoerrado.core.entitys.EntityBase;

@Entity @Table(name = "pessoa")
public class Pessoa extends EntityBase {

	private String nome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
