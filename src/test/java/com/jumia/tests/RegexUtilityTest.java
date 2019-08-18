package com.jumia.tests;

import com.jumia.model.Customer;
import java.util.List;

import com.jumia.utilities.Country;
import com.jumia.utilities.RegexUtility;
import com.jumia.utilities.State;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class RegexUtilityTest {

    private List<Customer> customerList = new ArrayList<>();

    @Before
    public void setup(){
        Customer customer = new Customer(1L, "Maria albertina", "(237) 34523456");
        customerList.add(customer);
        customer = new Customer(2L, "Mário Sá Carneiro", "(256) 345234564");
        customerList.add(customer);
        customer = new Customer(3L, "Sofia Aparício", "(251) 182233448");
        customerList.add(customer);
        // invalid
        customer = new Customer(4L, "Sónia Araújo", "(237) 6A0311634");
        customerList.add(customer);
        customer = new Customer(5L, "Álvaro Cunhal", "(212) 698054317");
        customerList.add(customer);
        // invalid country
        customer = new Customer(6L, "Jesus Cristo", "(351) 11111111");
        customerList.add(customer);
    }

    @Test
    public void givenCountryAndListCustomers_WhenFilterByCountry_ThenReturnList(){
        Country country = Country.Cameroon;
        List<Customer> customers = RegexUtility.filterByCountry(customerList, country);
        assertEquals(2, customers.size());
    }

    @Test
    public void givenCountryAndListCustomers_WhenFilterByCountry_ThenReturnEmptyList(){
        Country country = Country.Mozambique;
        List<Customer> customers = RegexUtility.filterByCountry(customerList, country);
        assertEquals(0, customers.size());
    }

    @Test
    public void givenCountryAndListCustomersNull_WhenFilterByCountry_ThenReturnEmptyList(){
        Country country = Country.Mozambique;
        List<Customer> customers = RegexUtility.filterByCountry(null, country);
        assertEquals(customers.size(), 0);
    }

    @Test
    public void givenCountryNullAndListCustomers_WhenFilterByCountry_ThenReturnEmptyList(){
        List<Customer> customers = RegexUtility.filterByCountry(customerList, null);
        assertEquals(0, customers.size());
    }

    @Test
    public void givenStateNullAndListCustomers_WhenFilterByState_ThenReturnEmptyList(){
        List<Customer> customers = RegexUtility.filterByState(customerList, null);
        assertEquals(0, customers.size());
    }

    @Test
    public void givenStateAndListCustomersNull_WhenFilterByState_ThenReturnEmptyList(){
        State state = State.valid;
        List<Customer> customers = RegexUtility.filterByState(null, state);
        assertEquals(0, customers.size());
    }

    @Test
    public void givenStateAndListCustomers_WhenFilterByState_ThenReturnInValidList(){
        State state = State.invalid;
        List<Customer> customers = RegexUtility.filterByState(customerList, state);
        assertEquals(customers.size(), 2);
    }

    @Test
    public void givenStateAndListCustomers_WhenFilterByState_ThenReturnValidList(){
        State state = State.valid;
        List<Customer> customers = RegexUtility.filterByState(customerList, state);
        assertEquals(customers.size(), 4);
    }

}
