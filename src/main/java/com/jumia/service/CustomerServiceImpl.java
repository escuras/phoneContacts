package com.jumia.service;

import com.jumia.model.Customer;
import com.jumia.repository.CustomerRepository;
import com.jumia.utilities.Country;
import com.jumia.utilities.RegexUtility;
import com.jumia.utilities.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return paginate(pageable,(List<Customer>) customerRepository.findAll());
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

    public Page<Customer> paginate(Pageable pageable, List<Customer> customers) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Customer> list;
        if (customers.size() < startItem) {
            list = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, customers.size());
            list = customers.subList(startItem, toIndex);
        }
        Page<Customer> customerPage = new PageImpl<Customer>(list, PageRequest.of(currentPage, pageSize), customers.size());
        return customerPage;
    }
}
