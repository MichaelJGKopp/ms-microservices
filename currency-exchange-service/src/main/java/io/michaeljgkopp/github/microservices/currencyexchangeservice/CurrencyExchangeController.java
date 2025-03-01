package io.michaeljgkopp.github.microservices.currencyexchangeservice;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
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

        // two ids assigned to this request by micrometer used to trace across microservices
        // INFO [currency-exchange,cd37d23b38409589e7930a2b31e28b51,0634cc7aab5809da] 18862 --- [currency-exchange] [nio-8000-exec-1] [cd37d23b38409589e7930a2b31e28b51-0634cc7aab5809da] i.m.g.m.c.CurrencyExchangeController     : retrieveExchangeValue called with USD to INR
        logger.info("retrieveExchangeValue called with {} to {}", from, to);

        CurrencyExchange currencyExchange = currencyExchangeService.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }

        String port = env.getProperty("local.server.port");
        currencyExchange.setEnvironment(port + " instance-id");

        return currencyExchange;
    }
}
