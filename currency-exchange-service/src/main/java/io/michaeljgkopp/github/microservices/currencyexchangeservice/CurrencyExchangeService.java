package io.michaeljgkopp.github.microservices.currencyexchangeservice;

public interface CurrencyExchangeService {

    CurrencyExchange findById(Long id);

    void save(CurrencyExchange currencyExchange);

    CurrencyExchange findByFromAndTo(String from, String to);
}
