package com.app.repository;

import java.util.List;
import java.util.UUID;

import com.app.config.WriteableRepository;
import com.app.entity.Role;
import com.app.enumeration.UserType;

public interface RoleRepository extends WriteableRepository<Role, UUID> {

	public Role findByRoleName(UserType roleName);

	public Role findRoleNameById(UUID id);
}
