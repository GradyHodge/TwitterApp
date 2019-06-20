package com.tts.MockingBirdApp.repository;

import org.springframework.data.repository.CrudRepository;

import com.tts.MockingBirdApp.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByRole(String role);
}
