package com.dev.backend.service;

import com.dev.backend.document.Customer;

import java.security.Principal;
import java.util.List;

public interface MemberService {
    List<Customer> getAllMemberInProvince(Principal principal);

}
