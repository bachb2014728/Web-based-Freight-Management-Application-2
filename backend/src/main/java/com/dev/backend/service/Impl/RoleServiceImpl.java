package com.dev.backend.service.Impl;

import com.dev.backend.document.Role;
import com.dev.backend.repository.RoleRepository;
import com.dev.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
