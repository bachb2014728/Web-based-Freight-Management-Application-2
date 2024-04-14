package com.dev.backend.service.Impl;

import com.dev.backend.document.Employee;
import com.dev.backend.document.UserDocument;
import com.dev.backend.repository.EmployeeRepository;
import com.dev.backend.repository.UserRepository;
import com.dev.backend.service.EmployeeService;
import com.dev.backend.web.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> getEmployess(Principal principal) {
        UserDocument user = userRepository.findByEmail(principal.getName()).get();
        Employee employee = employeeRepository.findByUser(user);
        System.out.print(employee);
        return null;
    }
}
