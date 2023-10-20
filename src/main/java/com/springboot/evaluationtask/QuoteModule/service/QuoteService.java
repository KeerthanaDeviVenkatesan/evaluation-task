package com.springboot.evaluationtask.QuoteModule.service;

import com.springboot.evaluationtask.DashboardModule.enity.Symbol;

public interface QuoteService {
    Symbol getSymbolDetails(String symbol);
}
