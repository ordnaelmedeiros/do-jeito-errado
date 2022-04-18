package com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaRepository;
import com.ordnaelmedeiros.dojeitoerrado.core.exceptions.ExceptionUtils;

@ApplicationScoped
public class PessoaDeleteService {

	@Inject PessoaRepository pessoaRepository;
	
	public void execute(UUID id) {
		pessoaRepository
			.findByIdOptional(id)
			.ifPresentOrElse(
				pessoaRepository::delete,
				ExceptionUtils::throwNotFoundException);
	}
	
}
