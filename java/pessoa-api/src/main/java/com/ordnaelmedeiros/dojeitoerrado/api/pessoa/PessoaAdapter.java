package com.ordnaelmedeiros.dojeitoerrado.api.pessoa;

import javax.enterprise.context.ApplicationScoped;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOBase;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOView;

@ApplicationScoped
public class PessoaAdapter {
	
	public Pessoa toEntity(PessoaDTOBase dto) {
		return toEntity(dto, new Pessoa());
	}
	
	public Pessoa toEntity(PessoaDTOBase dto, Pessoa ent) {
		ent.setNome(dto.getNome());
		return ent;
	}
	
	public PessoaDTOView toView(Pessoa ent) {
		var view = new PessoaDTOView();
		view.setId(ent.getId());
		view.setNome(ent.getNome());
		return view;
	}
	
}
