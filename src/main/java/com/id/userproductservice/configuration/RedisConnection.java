package com.id.userproductservice.configuration;

import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConnection {

    @Value(value = "${redis.host}")
    private String redisHost;
    @Value(value = "${redis.port}")
    private int redisPort;
    @Value(value = "${redis.password}")
    private String redisPassword;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);

        LettuceClientConfiguration lettuceClientConfiguration =  LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(30))
                .clientResources(ClientResources.builder().build())
                .build();

        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        return new LettuceConnectionFactory(redisStandaloneConfiguration,lettuceClientConfiguration);
    }

}
