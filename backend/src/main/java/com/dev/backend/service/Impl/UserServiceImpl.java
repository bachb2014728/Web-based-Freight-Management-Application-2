package com.dev.backend.service.Impl;

import com.dev.backend.repository.UserRepository;
import com.dev.backend.service.UserService;
import com.dev.backend.web.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

}
