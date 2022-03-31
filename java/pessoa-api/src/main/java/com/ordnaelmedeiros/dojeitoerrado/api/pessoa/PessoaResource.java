package com.ordnaelmedeiros.dojeitoerrado.api.pessoa;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOCreate;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOId;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOUpdate;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOView;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services.PessoaCreateService;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services.PessoaDeleteService;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services.PessoaReadService;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.services.PessoaUpdateService;

@Path("pessoa")
public class PessoaResource {
	
	@Inject PessoaCreateService createService;
	@Inject PessoaReadService readService;
	@Inject PessoaUpdateService updateService;
	@Inject PessoaDeleteService deleteService;
	
	@GET @Path("all")
	public List<PessoaDTOView> all() {
		return readService.all();
	}
	
	@Transactional
	@POST @ResponseStatus(201)
	public PessoaDTOId create(PessoaDTOCreate dto) {
		return createService.execute(dto);
	}
	
	@GET @Path("{id}")
	public PessoaDTOView read(UUID id) {
		return readService.read(id);
	}
	
	@Transactional
	@PUT @Path("{id}")
	public void update(@RestPath("id") UUID id, PessoaDTOUpdate dto) {
		updateService.execute(id, dto);
	}
	
	@Transactional
	@DELETE @Path("{id}")
	public void delete(@RestPath("id") UUID id) {
		deleteService.execute(id);
	}
	
}
