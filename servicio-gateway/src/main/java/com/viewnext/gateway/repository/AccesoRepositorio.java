package com.viewnext.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viewnext.gateway.entity.Users;

@Repository
public interface AccesoRepositorio extends JpaRepository<Users, String>{

	@Modifying
	@Query("update Users u set u.password = ?1 where u.username = ?2")
	int setPasswordFor(String password, String username);
}
