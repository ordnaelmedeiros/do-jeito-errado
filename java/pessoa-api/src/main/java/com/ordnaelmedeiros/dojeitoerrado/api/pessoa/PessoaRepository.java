package com.ordnaelmedeiros.dojeitoerrado.api.pessoa;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class PessoaRepository implements PanacheRepositoryBase<Pessoa, UUID> {

}
