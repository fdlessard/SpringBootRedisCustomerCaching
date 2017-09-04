package io.fdlessard.codebites.caching.controllers;


import io.fdlessard.codebites.caching.domain.Customer;
import io.fdlessard.codebites.caching.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/isAlive", produces = "application/json")
    public String isAlive() {
        LOGGER.info("CustomerController.isAlive()");
        return "Hello World from Customer Service";
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public Customer getCustomerById(@PathVariable long id) {
        LOGGER.info("CustomerController.getById({})", id);
        return customerService.getCustomerById(id);
    }

    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public List<Customer> getAllCustomers() {
        LOGGER.info("CustomerController.getAllCustomers()");
        return customerService.getAllCustomers();
    }

    @PostMapping(value = "/", produces = "application/json")
    public void createCustomer(@RequestBody Customer customer) {
        LOGGER.info("CustomerController.createCustomer({})", customer);
        customerService.createCustomer(customer);

    }
}
