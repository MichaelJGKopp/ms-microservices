package io.michaeljgkopp.github.microservices.currencyexchangeservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CurrencyExchangeController {

    private final Environment env;
    private final CurrencyExchangeService currencyExchangeService;

    @PostConstruct
    public void init() {

        List<CurrencyExchange> currencyExchanges = List.of(
                new CurrencyExchange(10_001L, "USD", "INR", new BigDecimal("65.00")),
                new CurrencyExchange(10_002L, "EUR", "INR", new BigDecimal("75.00")),
                new CurrencyExchange(10_003L, "AUD", "INR", new BigDecimal("25.00"))
                );

        currencyExchanges.forEach(currencyExchange -> currencyExchangeService.save(currencyExchange));
    }

    @Autowired
    public CurrencyExchangeController(Environment env, CurrencyExchangeService currencyExchangeService) {
        this.env = env;
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {

        CurrencyExchange currencyExchange = currencyExchangeService.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }

        String port = env.getProperty("local.server.port");
        currencyExchange.setEnvironment(port + " instance-id");

        return currencyExchange;
    }
}
