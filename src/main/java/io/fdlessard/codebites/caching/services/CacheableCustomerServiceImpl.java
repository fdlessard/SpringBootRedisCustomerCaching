package io.fdlessard.codebites.caching.services;

import io.fdlessard.codebites.caching.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service("CacheableCustomerServiceImpl")
public class CacheableCustomerServiceImpl extends CustomerServiceImpl {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    @Cacheable("Customer")
    public Customer getCustomerById(long id) {
        LOGGER.info("CacheableCustomerServiceImpl.getById({})", id);
        return super.getCustomerById(id);
    }


    @Override
    public List<Customer> getCustomers(List<Long> ids) {

        LOGGER.info("CacheableCustomerServiceImpl.getCustomers({})", ids);

        List<Customer> cachedCustomers = redisTemplate.opsForValue().multiGet(ids);

        Map<Long, Customer> customers = new HashMap<>();
        List<Long> uncachedCustomerIds = new ArrayList<>();
        for(int i = 0; i < ids.size(); i++) {
            if(cachedCustomers.get(i) != null) {
                customers.put(ids.get(i), cachedCustomers.get(i));
            } else {
                uncachedCustomerIds.add(ids.get(i));
            }
        }

        List<Customer> uncachedCustomers = super.getCustomers(uncachedCustomerIds);

        for(int i = 0; i < uncachedCustomerIds.size(); i++) {
            customers.put(uncachedCustomerIds.get(i), uncachedCustomers.get(i));
        }

        redisTemplate.opsForValue().multiSetIfAbsent(customers);


        return ids.stream().map(customers::get).collect(Collectors.toList());

    }

}
