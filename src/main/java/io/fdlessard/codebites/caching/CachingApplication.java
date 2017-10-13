package io.fdlessard.codebites.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableCaching
public class CachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingApplication.class, args);
	}


	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("localhost");
		factory.setPort(6379);
		factory.setUsePool(true);
		return factory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager() {
		return new RedisCacheManager(redisTemplate());
	}

}
