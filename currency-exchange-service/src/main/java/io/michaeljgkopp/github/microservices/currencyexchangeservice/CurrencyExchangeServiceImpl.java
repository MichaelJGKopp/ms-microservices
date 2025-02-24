package io.michaeljgkopp.github.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeRepository repository;

    @Autowired
    public CurrencyExchangeServiceImpl(CurrencyExchangeRepository repository) {
        this.repository = repository;
    }

    @Override
    public CurrencyExchange findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(CurrencyExchange currencyExchange) {
        repository.save(currencyExchange);
    }

    @Override
    public CurrencyExchange findByFromAndTo(String from, String to) {
        return repository.findByFromAndTo(from, to);
    }
}
