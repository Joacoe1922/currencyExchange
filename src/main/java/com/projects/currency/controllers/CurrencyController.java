package com.projects.currency.controllers;

import com.projects.currency.entities.Currency;
import com.projects.currency.errors.ErrorService;
import com.projects.currency.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/")
    public String index(ModelMap model) {

        List<Currency> currencies = currencyService.listAll();

        model.addAttribute("currencies", currencies);

        return "index";
    }

    @PostMapping("/")
    public String createCurrency(ModelMap model, @RequestParam String name, @RequestParam Double value) throws ErrorService {

        try {
            currencyService.create(name, value);

            model.put("success", "Currency created successfully");

            List<Currency> currencies = currencyService.listAll();

            model.addAttribute("currencies", currencies);

            return "index";

        } catch (ErrorService e){

            model.put("error", "Oops, something went wrong");

            return "index";
        }
    }

    @PostMapping("/convert")
    public String convertCurrency(ModelMap model, @RequestParam String id1, @RequestParam String id2) throws ErrorService {

        try {
            String msn = currencyService.convert(id1, id2);

            List<Currency> currencies = currencyService.listAll();

            model.addAttribute("currencies", currencies);

            model.put("success", msn);

            return "index";

        } catch (ErrorService e) {

            model.put("error", "Oops, something went wrong");

            return "index";
        }

    }

}
