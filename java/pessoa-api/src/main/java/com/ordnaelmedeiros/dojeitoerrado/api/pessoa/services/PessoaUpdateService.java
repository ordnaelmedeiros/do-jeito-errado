package com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaAdapter;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaRepository;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOUpdate;
import com.ordnaelmedeiros.dojeitoerrado.core.exceptions.ExceptionUtils;

@ApplicationScoped
public class PessoaUpdateService {
	
	@Inject PessoaRepository pessoaRepository;
	@Inject PessoaAdapter pessoaAdapter;
	
	public void execute(UUID id, PessoaDTOUpdate dto) {
		pessoaRepository
			.findByIdOptional(id)
			.ifPresentOrElse(
				e -> {
					pessoaAdapter.toEntity(dto, e);
				},
				ExceptionUtils::throwNotFoundException);
	}
	
}
