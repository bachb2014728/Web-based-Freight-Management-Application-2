package com.dev.backend.service;

import com.dev.backend.web.dto.CreateEmployee;
import com.dev.backend.web.dto.EmployeeDto;

import java.security.Principal;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getEmployees(Principal principal);

    void saveEmployee(CreateEmployee employee, Principal principal);

    CreateEmployee getEmployee(String id);

    EmployeeDto getEmployeeShow(String id );

    EmployeeDto updateEmployee(String id, CreateEmployee employeeUpdate);

    void removeEmployee(String id);

}
