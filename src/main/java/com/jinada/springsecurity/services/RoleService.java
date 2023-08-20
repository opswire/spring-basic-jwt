package com.jinada.springsecurity.services;

import com.jinada.springsecurity.models.Role;
import com.jinada.springsecurity.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getUserRole() {
        return roleRepository.findByTitle("ROLE_USER").get();
    }
}

