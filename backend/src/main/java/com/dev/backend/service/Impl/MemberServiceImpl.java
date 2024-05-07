package com.dev.backend.service.Impl;

import com.dev.backend.document.Customer;
import com.dev.backend.document.Province;
import com.dev.backend.document.Store;
import com.dev.backend.repository.CustomerRepository;
import com.dev.backend.repository.ProvinceRepository;
import com.dev.backend.service.MemberService;
import com.dev.backend.service.helper.ConvertStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final CustomerRepository customerRepository;
    private final ConvertStore convertStore;
    private final ProvinceRepository provinceRepository;

    @Override
    public List<Customer> getAllMemberInProvince(Principal principal) {
        Store store = convertStore.getStoreByEmail(principal.getName());

        Province province = provinceRepository.findByName(store.getProvince()).get();
        List<Customer> customers = customerRepository.findAll();
        customers.removeIf(item -> !(item.getAddress().getProvince().getName()).equals(province.getName()));
        return customers;
    }
}
