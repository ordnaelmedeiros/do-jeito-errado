package com.ordnaelmedeiros.dojeitoerrado.api.pessoa;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOCreate;
import com.ordnaelmedeiros.dojeitoerrado.api.pessoa.models.PessoaDTOUpdate;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@QuarkusTest
@TestHTTPEndpoint(PessoaResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PessoaResourceTest {

	private static UUID id;
	
	@Test @Order(1)
	void create() {
		// GIVEN
		var dto = new PessoaDTOCreate();
		dto.setNome("teste");
		// WHEN
		id = when(dto).post().then()
			.statusCode(201)
			.body("id", notNullValue())
			.extract().jsonPath().getUUID("id");
		// THEN
		when().get("/{id}", id).then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("nome", is(dto.getNome()));
	}
	
	@Test @Order(2)
	void update() {
		// GIVEN
		var dto = new PessoaDTOUpdate();
		dto.setNome("teste 2");
		// WHEN
		when(dto).put("{id}", id).then().statusCode(204);
		// THEN
		when().get("{id}", id).then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("nome", is(dto.getNome()));
	}
	
	@Test @Order(3)
	void delete() {
		// WHEN
		when().delete("{id}", id).then().statusCode(204);
		// THEN
		when().get("{id}", id).then().statusCode(404);
	}
	
	private RequestSpecification when() {
		return given().when()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON);
	}
	private RequestSpecification when(Object dto) {
		return when().body(dto);
	}
	
}
