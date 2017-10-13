package io.fdlessard.codebites.redis.caching.services;

import io.fdlessard.codebites.redis.caching.domain.Customer;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CustomerService {
    @Cacheable("Customer")
    Customer getCustomerById(long id);

    List<Customer> getAllCustomers();

    List<Customer> getCustomers(List<Long> ids);

    void createCustomer(Customer customer);
}
