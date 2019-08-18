package com.jumia.service;

import com.jumia.model.Customer;
import com.jumia.repository.CustomerRepository;
import com.jumia.utilities.Country;
import com.jumia.utilities.RegexUtility;
import com.jumia.utilities.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return getCustomers();
    }

    private List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public List<Customer> getInvalidCustomers() {
        return RegexUtility.filterByState(getCustomers(), State.invalid);
    }

    @Override
    public List<Customer> getValidCustomers() {
        return RegexUtility.filterByState(getCustomers(), State.valid);
    }

    @Override
    public List<Customer> getCustomersByCountry(Country country) {
        return RegexUtility.filterByCountry(getCustomers(), country);
    }

    @Override
    public List<Customer> getCustomersByCountry(String name) {
        Country country = Country.findCountry(name);
        if(country == null) {
            return new ArrayList<>();
        }
        return getCustomersByCountry(country);
    }
}
