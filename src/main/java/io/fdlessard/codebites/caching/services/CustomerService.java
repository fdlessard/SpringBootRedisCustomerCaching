package io.fdlessard.codebites.caching.services;

import io.fdlessard.codebites.caching.domain.Customer;
import io.fdlessard.codebites.caching.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Cacheable("Customer")
    public Customer getCustomerById(long id) {

        LOGGER.info("CustomerService.getById({})", id);
        pause();
        return customerRepository.findOne(id);
    }

    public List<Customer> getAllCustomers() {

        LOGGER.info("CustomerService.getAllCustomers()");
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    public void createCustomer(Customer customer) {

        LOGGER.info("CustomerService.createCustomer()");
        customerRepository.save(customer);
    }

    private void pause() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {

        }
    }
}
