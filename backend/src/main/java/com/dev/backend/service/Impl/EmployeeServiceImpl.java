package com.dev.backend.service.Impl;

import com.dev.backend.document.*;
import com.dev.backend.repository.*;
import com.dev.backend.service.EmployeeService;
import com.dev.backend.web.dto.location.Address;
import com.dev.backend.web.dto.CreateEmployee;
import com.dev.backend.web.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public List<EmployeeDto> getEmployees(Principal principal) {
        UserDocument user = userRepository.findByEmail(principal.getName()).get();
        Employee employee = employeeRepository.findByUser(user);
        if(employee.getEmployees() == null){
            return null;
        }
        List<Employee> employees = employee.getEmployees();
        return employees.stream().map(this::mapEmployeeToEmployeeDto).collect(Collectors.toList());
    }
    public EmployeeDto mapEmployeeToEmployeeDto(Employee employee){
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .address(employee.getAddress())
                .user(employee.getUser())
                .phone(employee.getPhone())
                .identifier(employee.getIdentifier())
                .date(employee.getDate())
                .gender(employee.getGender())
                .build();
    }
    @Override
    public void saveEmployee(CreateEmployee employeeNew, Principal principal) {
        if(employeeNew.getRole() == null){
            Role role = roleRepository.findByName("STAFF").get();
            employeeNew.setRole(role);
        }
        UserDocument user = UserDocument.builder()
                .email(employeeNew.getEmail())
                .password(passwordEncoder.encode(employeeNew.getPassword()))
                .roles(Collections.singletonList(employeeNew.getRole()))
                .isEnabled(true)
                .build();
        UserDocument userNew = userRepository.save(user);
        Address address = getAddress(employeeNew);
        Employee employee = Employee.builder()
                .firstName(employeeNew.getFirstName())
                .lastName(employeeNew.getLastName())
                .address(address)
                .phone(employeeNew.getPhone())
                .identifier(employeeNew.getIdentifier())
                .user(userNew)
                .gender(employeeNew.getGender())
                .date(employeeNew.getDate())
                .build();
        Employee employee1 = employeeRepository.save(employee);
        UserDocument userDocument = userRepository.findByEmail(principal.getName()).get();
        Employee employeeAmin = employeeRepository.findByUser(userDocument);
        if(employeeAmin.getEmployees() == null){
            employeeAmin.setEmployees(Collections.singletonList(employee1));
        }else{
            employeeAmin.getEmployees().add(employee1);
        }
        employeeRepository.save(employeeAmin);
    }

    @Override
    public CreateEmployee getEmployee(String id) {
        Employee employee = employeeRepository.findById(id).get();
        return mapEmployeeToEmployeeCreate(employee);
    }

    @Override
    public EmployeeDto getEmployeeShow(String id) {
        Employee employee = employeeRepository.findById(id).get();
        return mapEmployeeToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(String id, CreateEmployee employeeUpdate) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setFirstName(employeeUpdate.getFirstName());
        employee.setLastName(employeeUpdate.getLastName());
        if(employeeUpdate.getDate() != null){
            employee.setDate(employeeUpdate.getDate());
        }
        employee.setIdentifier(employeeUpdate.getIdentifier());
        employee.setPhone(employeeUpdate.getPhone());

        UserDocument userDocument = userRepository.findByEmail(employeeUpdate.getEmail()).get();
        userDocument.setRoles(Collections.singletonList(employeeUpdate.getRole()));
        userDocument.setEnabled(employeeUpdate.isEnabled());
        UserDocument userUpdate = userRepository.save(userDocument);

        employee.setUser(userUpdate);
        Address address = getAddress(employeeUpdate);
        employee.setAddress(address);

        employee.setGender(employeeUpdate.getGender());
        Employee employeeNew = employeeRepository.save(employee);
        return mapEmployeeToEmployeeDto(employeeNew);
    }

    @Override
    public void removeEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    public CreateEmployee mapEmployeeToEmployeeCreate(Employee employee){
        return CreateEmployee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .date(employee.getDate())
                .district(employee.getAddress().getDistrict().getName())
                .province(employee.getAddress().getProvince().getName())
                .ward(employee.getAddress().getWard().getName())
                .role((Role) employee.getUser().getRoles())
                .gender(employee.getGender())
                .phone(employee.getPhone())
                .identifier(employee.getIdentifier())
                .build();
    }

    @NotNull
    private Address getAddress(CreateEmployee employeeNew) {
        Address address = Address.builder().build();
        if(!provinceRepository.existsByName(employeeNew.getProvince())){
            Ward ward = Ward.builder().name(employeeNew.getWard()).build();
            Ward wardNew = wardRepository.save(ward);
            District district = District
                    .builder().name(employeeNew.getDistrict())
                    .wards(Collections.singletonList(wardNew))
                    .build();
            District districtNew  = districtRepository.save(district);
            Province province = Province.builder()
                    .name(employeeNew.getProvince())
                    .districts(Collections.singletonList(districtNew))
                    .build();
            Province provinceNew = provinceRepository.save(province);
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(provinceNew);
        }else if (!districtRepository.existsByName(employeeNew.getDistrict())){
            Ward ward = Ward.builder().name(employeeNew.getWard()).build();
            Ward wardNew = wardRepository.save(ward);
            District district = District
                    .builder().name(employeeNew.getDistrict())
                    .wards(Collections.singletonList(wardNew))
                    .build();
            District districtNew  = districtRepository.save(district);
            Province province = provinceRepository.findByName(employeeNew.getProvince()).get();
            province.getDistricts().add(districtNew);
            Province provinceNew = provinceRepository.save(province);
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(provinceNew);
        }else{
            Ward ward = Ward.builder().name(employeeNew.getWard()).build();
            Ward wardNew = wardRepository.save(ward);
            District district = districtRepository.findByName(employeeNew.getDistrict()).get();
            district.getWards().add(wardNew);
            District districtNew = districtRepository.save(district);
            Province province = provinceRepository.findByName(employeeNew.getProvince()).get();
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(province);
        }
        return address;
    }
}
