package com.jumia.controller;

import com.jumia.model.Customer;
import com.jumia.service.CustomerService;
import com.jumia.utilities.Country;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PhoneController {

    @Value("${welcome.message}")
    private String message;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String allCustomers(Model model,  @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);

        Page<Customer> customerPage = customerService.getAllCustomers(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("customerPage", customerPage);

        int totalPages = customerPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        Iterable<Customer> customers = customerService.getCustomersByCountry(Country.Ethiopia);
        model.addAttribute("customers", customers);

        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);

        return "welcome"; //view
    }

}
