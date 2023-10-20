package com.springboot.evaluationtask.QuoteModule.controller;

import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.QuoteModule.service.Impl.QuoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quote")
public class QuoteController {
    @Autowired
    QuoteServiceImpl quoteService;
    @GetMapping("/symbolDetails/{symbol}")
    public ResponseEntity<String> getSymbolDetails(@PathVariable String symbol) {
        try {
            // Call the service to fetch symbol details
            Symbol symbol1 = quoteService.getSymbolDetails(symbol);

            if (symbol1 != null) {
                return ResponseEntity.ok("displayed"+symbol1);
            } else {
                return ResponseEntity.notFound().build(); // Symbol not found
            }
        } catch (Exception e) {
            return ResponseEntity.ok("not fetched");
        }
    }

}
