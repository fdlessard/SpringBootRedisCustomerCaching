package io.fdlessard.codebites.caching.controllers;


import io.fdlessard.codebites.caching.domain.Customer;
import io.fdlessard.codebites.caching.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private CustomerService customerService;

    private RedisTemplate redisTemplate;


    public CustomerController(
            @Qualifier("CacheableCustomerServiceImpl") CustomerService customerService,
            RedisTemplate redisTemplate
    ) {
        this.customerService = customerService;
        this.redisTemplate = redisTemplate;
    }

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

    @GetMapping(value = "/ids/{ids}", produces = "application/json")
    @ResponseBody
    public List<Customer> getCustomers(@PathVariable List<Long> ids) {
        LOGGER.info("CustomerController.getCustomers()");
        return customerService.getCustomers(ids);
    }

    @PostMapping(value = "/", produces = "application/json")
    public void createCustomer(@RequestBody Customer customer) {
        LOGGER.info("CustomerController.createCustomer({})", customer);
        customerService.createCustomer(customer);
    }

    @GetMapping(value = "/cache/{id}", produces = "application/json")
    @ResponseBody
    public Customer getCachedCustomerById(@PathVariable long id) {
        LOGGER.info("CustomerController.getCachedCustomerById({})", id);
        return (Customer) redisTemplate.opsForValue().get(id);
    }

}
