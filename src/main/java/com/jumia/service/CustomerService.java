package com.jumia.service;

import com.jumia.model.Customer;
import com.jumia.utilities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Page<Customer> getAllCustomers(Pageable pageable);
    List<Customer> getInvalidCustomers();
    List<Customer> getValidCustomers();
    List<Customer> getCustomersByCountry(Country country);
}
