package com.sia.batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {

	@Id
	private Long id;

	private String name;

	private String memo;

	protected Member() {
	}

	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void changeMemo(String memo) {
		this.memo = memo;
	}
}