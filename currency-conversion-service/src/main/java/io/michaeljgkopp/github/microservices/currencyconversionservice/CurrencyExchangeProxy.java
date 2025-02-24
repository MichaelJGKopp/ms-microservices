package io.michaeljgkopp.github.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// same name as in application.properties of the other microservice
//@FeignClient(name="currency-exchange", url="localhost:8000")
@FeignClient(name="currency-exchange")  // leave url out for eureka
public interface CurrencyExchangeProxy {

    // CurrencyExchange JSON returned automatically mapped to CurrencyConversion
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);
}