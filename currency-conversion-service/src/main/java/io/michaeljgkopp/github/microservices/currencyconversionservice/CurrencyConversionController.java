package io.michaeljgkopp.github.microservices.currencyconversionservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeProxy proxy;

    public CurrencyConversionController(CurrencyExchangeProxy proxy) {
        this.proxy = proxy;
    }

    // http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(
                currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(quantity),
                currencyConversion.getEnvironment() + ", rest template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(
                currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(quantity),
                currencyConversion.getEnvironment() + ", feign");
    }
}
