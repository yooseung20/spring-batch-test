package com.sia.batch.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MemberData {

	@Id
	@JdbcTypeCode(Types.VARCHAR)
	private UUID id;

	private Long memberId;

	private String data;

	protected MemberData() {
	}

	public MemberData(UUID id, Long memberId, String data) {
		this.id = id;
		this.memberId = memberId;
		this.data = data;
	}
	public UUID getId() {
		return id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getData() {
		return data;
	}
}
