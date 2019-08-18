package com.jumia.repository;

import org.springframework.data.repository.CrudRepository;

import com.jumia.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByName(String name);
    Customer findByPhone(String phone);
}