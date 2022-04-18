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
import javax.ws.rs.core.Response;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.Pessoa;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaAdapter;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.PessoaRepository;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOView;

@ApplicationScoped
public class PessoaReadService {

	@Inject PessoaRepository pessoaRepository;
	@Inject PessoaAdapter pessoaAdapter;
	
	public Response read(UUID id) {
		
		var pessoa = pessoaRepository
			.findByIdOptional(id)
			.orElseThrow(NotFoundException::new);
		
		var pETag = pessoaToETag(pessoa);
		
		return Response
			.ok(pessoaAdapter.toView(pessoa))
			.lastModified(pETag.lastModified)
			.tag(pETag.tag)
			.build();
		
	}

	public List<PessoaDTOView> all() {
		return pessoaRepository
			.streamAll()
			.map(pessoaAdapter::toView)
			.collect(toList());
	}
	
	private PessoaETag pessoaToETag(Pessoa pessoa) {
		var pTag = new PessoaETag();
		pTag.tag = new EntityTag(pessoa.getVersao().toString());
		pTag.lastModified = Date.from(pessoa.getUpdateAt().atZone(ZoneId.systemDefault()).toInstant());
		return pTag;
	}
	
	private class PessoaETag {
		public EntityTag tag;
		public Date lastModified;
	}
	
}
