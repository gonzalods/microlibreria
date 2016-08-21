package com.viewnext.gateway.entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Users {

	@Id
	private String username;
	private String password;
	private boolean enabled;
	@ElementCollection
	@CollectionTable(name="authorities",
			joinColumns=@JoinColumn(name="username"))
	@Column(name="authority")
	private Set<String> authority;
	
	public Users(String username, String password, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	public Users() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<String> getAuthority() {
		return authority;
	}
	public void setAuthority(Set<String> authority) {
		this.authority = authority;
	}
	
}
