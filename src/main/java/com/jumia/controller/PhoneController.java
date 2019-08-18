package com.jumia.controller;

import com.jumia.model.Customer;
import com.jumia.service.CustomerService;
import com.jumia.utilities.Country;

import java.util.ArrayList;
import java.util.List;

import com.jumia.utilities.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PhoneController {

    @Value("${welcome.message}")
    private String message;

    @Autowired
    private CustomerService customerService;

    private final int MAX_ROW_ITEMS = 10;
    private final int DEFAULT_PAGE = 1;

    @GetMapping("/")
    public String allCustomers(Model model,  @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(MAX_ROW_ITEMS);
        Page<Customer>  customerPage = paginate(PageRequest.of(currentPage - 1, pageSize), customerService.getAllCustomers());
        buildPage(model, customerPage, null, null);
        return "customers"; //view
    }

    @GetMapping("/country")
    public String customersByCountry(@RequestParam(value = "country", required = true) String country, Model model,
                                @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(MAX_ROW_ITEMS);
        Page<Customer>  customerPage = paginate(PageRequest.of(currentPage - 1, pageSize), customerService.getCustomersByCountry(country));
        buildPage(model, customerPage, country, null);
        return "customers"; //view
    }

    @GetMapping("/state/{state}")
    public String customersByState(@PathVariable(value = "state", required = true) String state, Model model,
                                @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(MAX_ROW_ITEMS);
        Page<Customer> customerPage;
        if (State.valid.name().equals(state)) {
            customerPage = paginate(PageRequest.of(currentPage - 1, pageSize), customerService.getValidCustomers());
        } else {
            customerPage = paginate(PageRequest.of(currentPage - 1, pageSize), customerService.getInvalidCustomers());
        }
        buildPage(model, customerPage, null, state);
        return "customers"; //view
    }

    private void buildPage(Model model, Page<Customer>  customerPage, String country, String state){
        model.addAttribute("customerPage", customerPage);
        int totalPages = customerPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("countries", listCountries());
        if(country != null){
            model.addAttribute("country", country);
        }
        if(state != null){
            model.addAttribute("state", state);
        }
    }

    private Page<Customer> paginate(Pageable pageable, List<Customer> customers) {
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

    private List<String> listCountries(){
        Country[] countries =  Country.values();
        List<String> names = new ArrayList<>();
        for(Country country : countries) {
            names.add(country.name());
        }
        return names;
    }

}
