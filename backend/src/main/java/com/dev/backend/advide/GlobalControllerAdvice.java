package com.dev.backend.advide;

import com.dev.backend.document.Employee;
import com.dev.backend.document.Role;
import com.dev.backend.document.Store;
import com.dev.backend.document.UserDocument;
import com.dev.backend.repository.EmployeeRepository;
import com.dev.backend.repository.StoreRepository;
import com.dev.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final StoreRepository storeRepository;
    @ModelAttribute("username")
    public String getUsername(Principal principal) {
        return (principal != null) ? principal.getName() : "";
    }
    @ModelAttribute("name")
    public String getName(Principal principal){
        if (principal == null) {
            return "";
        }
        UserDocument user = userRepository.findByEmail(principal.getName()).orElse(null);
        if(employeeRepository.findByUser(user) !=null){
            Employee employee = employeeRepository.findByUser(user);
            if(employee.getFirstName() != null && employee.getLastName() != null){
                return employee.getFirstName()+ " "+ employee.getLastName();
            }else{
                return principal.getName();
            }
        }
        return principal.getName();
    }
    @ModelAttribute("store_address")
    public String getStore(Principal principal){
        if (principal == null) {
            return "";
        }
        UserDocument user = userRepository.findByEmail(principal.getName()).orElse(null);
        assert user != null;
        Role role = user.getRoles().get(0);
        if(employeeRepository.findByUser(user) !=null && (Objects.equals(role.getName(), "ADMIN")) ){
            Employee employee = employeeRepository.findByUser(user);
            Store store = storeRepository.findByEmployee(employee);
            return store.getAddress();
        }
        if(employeeRepository.findByUser(user) !=null && (Objects.equals(role.getName(), "STAFF")) ){
            Employee employee = employeeRepository.findByUser(user);
            for (Employee item : employeeRepository.findAll()){
                if(item.getEmployees() != null){
                    for(Employee employeeItem : item.getEmployees()){
                        if(employee.equals(employeeItem)){
                            Store store = storeRepository.findByEmployee(item);
                            return store.getAddress();
                        }
                    }
                }
            }

        }
        return null;
    }
    @ModelAttribute("role")
    public String getRole(Principal principal){
        if (principal == null) {
            return "";
        }
        UserDocument user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user != null && !user.getRoles().isEmpty()) {
            List<String> roles = new ArrayList<>();
            for (Role item : user.getRoles()){
                roles.add(item.getName());
            }
            return roles.get(0);
        }
        return "";
    }

}
