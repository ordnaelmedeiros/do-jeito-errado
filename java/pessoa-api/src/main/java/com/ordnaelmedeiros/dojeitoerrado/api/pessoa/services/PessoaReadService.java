package com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services;

import static java.util.stream.Collectors.toList;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaAdapter;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaRepository;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOView;

@ApplicationScoped
public class PessoaReadService {

	@Inject PessoaRepository pessoaRepository;
	@Inject PessoaAdapter pessoaAdapter;
	
	public Response read(UUID id, Request request) {
		
		var pessoa = pessoaRepository
			.findByIdOptional(id)
			.orElseThrow(NotFoundException::new);
		
		Date lastModified = Date.from(pessoa.getUpdateAt().atZone(ZoneId.systemDefault()).toInstant());
		EntityTag tag = new EntityTag("v"+pessoa.getVersao());
		
		ResponseBuilder conditionalResponse = request.evaluatePreconditions(lastModified, tag);
		if (conditionalResponse != null) {
            return conditionalResponse.build();
		} else {
			return Response
				.ok(pessoaAdapter.toView(pessoa))
				.lastModified(lastModified)
				.tag(tag)
				.build();
		}
		
	}
	
	public List<PessoaDTOView> all() {
		return pessoaRepository
			.streamAll()
			.map(pessoaAdapter::toView)
			.collect(toList());
	}
	
}
