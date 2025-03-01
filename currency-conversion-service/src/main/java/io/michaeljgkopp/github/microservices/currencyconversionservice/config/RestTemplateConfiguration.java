package io.michaeljgkopp.github.microservices.currencyconversionservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// true/default: Ensures Spring-managed singleton beans even when calling methods within the same config class.
// false: Improves performance but may lead to duplicate beans when calling @Bean methods manually.
@Configuration(proxyBeanMethods = false)
public class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
