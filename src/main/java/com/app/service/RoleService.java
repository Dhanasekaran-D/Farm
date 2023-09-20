package com.app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.RoleDAO;
import com.app.dao.UserDAO;
import com.app.entity.Role;
import com.app.enumeration.UserType;
import com.app.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class RoleService {
	private @NonNull RoleRepository roleRepository;
	private @NonNull RoleDAO roleDAO;

	public void save(Role role) {
		roleRepository.save(role);
	}

	public Object get() {
		return roleRepository.findAll();
	}

	public Object getByid(UUID id) {
		return roleRepository.findById(id);
	}

	public void udate(Role role) {
		roleRepository.saveAndFlush(role);
	}

	public Role findByName(UserType roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(roleName);
	}

	public void delete(UUID roleId) {
		roleRepository.deleteById(roleId);
		
	}

	public Optional<Role> getid(UUID id) {
		return roleRepository.findById(id);
	}

	public Role findByNameExcludeId(UserType roleName, UUID id) {
		return roleDAO.findByNameExcludeId(roleName,id);
	}
}
