package com.projects.currency.services;

import com.projects.currency.entities.Currency;
import com.projects.currency.errors.ErrorService;
import com.projects.currency.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Transactional
    public void create(String name, Double value) throws ErrorService {

        try {

            validate(name, value);

            Currency currency = new Currency();

            currency.setName(name);
            currency.setValue(value);
            currency.setStatus(true);

            currencyRepository.save(currency);
        } catch (ErrorService e) {
            throw new ErrorService("Oops, something were wrong");
        }
    }

    @Transactional(readOnly = true)
    public List<Currency> listAll() {
        return currencyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public String convert(String id1, String id2) throws ErrorService{

        if (id1.isEmpty() || id2.isEmpty()) {
            throw new ErrorService("Id cannot be empty");
        }

        Optional<Currency> currency1 = currencyRepository.findById(id1);
        Optional<Currency> currency2 = currencyRepository.findById(id2);

        double value1 = currency1.get().getValue();
        double value2 = currency2.get().getValue();

        double valueConverted = value2 / value1;

        String msn = "1 " + currency1.get().getName() + " es igual a: " + valueConverted + " " + currency2.get().getName();

        return msn;
    }

    @Transactional(readOnly = true)
    public void validate(String name, Double value) throws ErrorService {
        if (name.isEmpty()) {
            throw new ErrorService("The name cannot be empty");
        }

        if (value < 0) {
            throw new ErrorService("The value cannot be negative or zero");
        }
    }
}
