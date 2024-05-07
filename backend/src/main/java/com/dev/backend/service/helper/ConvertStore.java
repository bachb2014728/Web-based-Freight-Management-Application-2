package com.dev.backend.service.helper;

import com.dev.backend.document.Employee;
import com.dev.backend.document.Store;
import com.dev.backend.document.UserDocument;
import com.dev.backend.repository.EmployeeRepository;
import com.dev.backend.repository.StoreRepository;
import com.dev.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertStore {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final StoreRepository storeRepository;
    public Store getStoreByEmail(String email){
        if(userRepository.existsByEmail(email)){
            UserDocument userDocument = userRepository.findByEmail(email).get();
            Employee employee = employeeRepository.findByUser(userDocument);
            return storeRepository.findByEmployee(employee);
        }else {
            return null;
        }
    }
    public Employee getEmployeeByEmail(String email){
        if(userRepository.existsByEmail(email)){
            UserDocument userDocument = userRepository.findByEmail(email).get();
            Employee employee = employeeRepository.findByUser(userDocument);
            if(employee.getEmployees() == null){
                for (Employee item : employeeRepository.findAll()){
                    if(item.getEmployees() != null){
                        for(Employee employeeItem : item.getEmployees()){
                            if(employee.equals(employeeItem)){
                                return  item;
                            }
                        }
                    }
                }
            }else{
                return employee;
            }
        }
        return null;
    }
}
