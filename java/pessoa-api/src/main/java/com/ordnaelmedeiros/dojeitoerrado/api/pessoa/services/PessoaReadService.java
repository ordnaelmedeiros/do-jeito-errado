package com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaAdapter;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaRepository;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOView;

@ApplicationScoped
public class PessoaReadService {

	@Inject PessoaRepository pessoaRepository;
	@Inject PessoaAdapter pessoaAdapter;
	
	public PessoaDTOView read(UUID id) {
		return pessoaRepository
			.findByIdOptional(id)
			.map(pessoaAdapter::toView)
			.orElseThrow(NotFoundException::new);
	}
	
	public List<PessoaDTOView> all() {
		return pessoaRepository
			.streamAll()
			.map(pessoaAdapter::toView)
			.collect(toList());
	}

}
