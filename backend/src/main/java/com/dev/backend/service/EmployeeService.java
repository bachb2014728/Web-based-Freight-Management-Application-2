package com.dev.backend.service;

import com.dev.backend.web.dto.EmployeeDto;

import java.security.Principal;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getEmployess(Principal principal);
}
