package com.fps.inspector.auth;

public enum RolesEnum {
	USUARIO("ROLE001"),
	ADMIN("ROLE002");

	private String role;

	public String getRole() {
		return this.role;
	}

	RolesEnum(String role) {
		this.role = role;
	}
}
