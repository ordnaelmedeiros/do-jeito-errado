package com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaAdapter;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaRepository;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOCreate;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOId;

@ApplicationScoped
public class PessoaCreateService {
	
	@Inject PessoaRepository pessoaRepository;
	@Inject PessoaAdapter pessoaAdapter;
	
	public PessoaDTOId execute(PessoaDTOCreate dto) {
		var ent = pessoaAdapter.toEntity(dto);
		pessoaRepository.persist(ent);
		return new PessoaDTOId(ent.getId());
	}
	
}
