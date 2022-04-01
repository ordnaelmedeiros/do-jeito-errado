package com.ordnaelmedeiros.dojeitoerrado.core.entitys;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class EntityBase {

	@Id
	private UUID id;
	
	@Version
	private Long versao;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createAt;
	
	@UpdateTimestamp
	private LocalDateTime updateAt;
	
	@PrePersist
	void prePersist() {
		id = UUID.randomUUID();
	}
	
	public UUID getId() {
		return id;
	}
	public Long getVersao() {
		return versao;
	}
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public LocalDateTime getUpdateAt() {
		return updateAt;
	}
	
}
