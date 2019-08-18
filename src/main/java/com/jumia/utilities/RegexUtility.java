package com.jumia.utilities;

import com.jumia.model.Customer;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtility {

    public static void main(String[] args) {
        Customer customer = new Customer(1L, "Maria albertina", "(237) 34523456");
        System.out.println(filterByState(Arrays.asList(customer), State.valid));
    }

    public static List<Customer> filterByCountry(List<Customer> customers, Country country) {
        if(customers == null || country == null) {
            return new ArrayList<>();
        }
        Pattern pattern = Pattern.compile("\\(" + country.getCode() +"\\)\\ (.*)");
        List<Customer> newCustomers = new ArrayList<>();
        for(Customer customer : customers){
            Matcher matcher = pattern.matcher(customer.getPhone());
            if (matcher.matches())  {
                newCustomers.add(customer);
            }
        }
        return newCustomers;
    }

    private static Country getCountry(Customer customer){
        if(customer == null) {
            return null;
        }
        String phone = customer.getPhone();
        if(phone == null || phone.length() - 1 < Country.COUNTRY_END_CODE_INDEX) {
            return null;
        }
        for(Country country : Country.values()) {
            if(country.getCode().equals(phone.substring(Country.COUNTRY_START_CODE_INDEX,Country.COUNTRY_END_CODE_INDEX))) {
                return country;
            }
        }
        return null;
    }

    public static List<Customer> filterByState(List<Customer> customers, State state) {
        if(customers == null || state == null) {
            return new ArrayList<>();
        }
        List<Customer> validContacts = new ArrayList<>();
        List<Customer> inValidContacts = new ArrayList<>();
        for(Customer customer : customers){
            Country country = getCountry(customer);
            if(country == null) {
                inValidContacts.add(customer);
                continue;
            }
            Pattern pattern = Pattern.compile(country.getValidRegex());
            Matcher matcher = pattern.matcher(customer.getPhone());
            if (matcher.matches())  {
                validContacts.add(customer);
            } else {
                inValidContacts.add(customer);
            }
        }
        if(State.valid.equals(state)) {
            return validContacts;
        }
        return inValidContacts;
    }
}
