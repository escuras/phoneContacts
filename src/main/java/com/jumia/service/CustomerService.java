package com.jumia.service;

import com.jumia.model.Customer;
import com.jumia.utilities.Country;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    List<Customer> getInvalidCustomers();
    List<Customer> getValidCustomers();
    List<Customer> getCustomersByCountry(Country country);
    List<Customer> getCustomersByCountry(String country);
}
