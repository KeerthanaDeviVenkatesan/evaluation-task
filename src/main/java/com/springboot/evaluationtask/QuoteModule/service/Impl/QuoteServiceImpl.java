package com.springboot.evaluationtask.QuoteModule.service.Impl;


import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.DashboardModule.repository.SymbolRepository;
import com.springboot.evaluationtask.QuoteModule.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuoteServiceImpl implements QuoteService {
    @Autowired
    SymbolRepository symbolRepository;
    @Override
    public Symbol getSymbolDetails(String symbol) {
        Optional<Symbol> symbolOptional = symbolRepository.findBySymbol(symbol);

        if (symbolOptional.isPresent()) {
            Symbol symbolEntity = symbolOptional.get();
            Symbol symbolDetails = new Symbol();
            symbolDetails.setSymbolId(symbolEntity.getSymbolId());
            symbolDetails.setSymbol(symbolEntity.getSymbol());
            symbolDetails.setSymbolName(symbolEntity.getSymbolName());
            symbolDetails.setIndexName(symbolEntity.getIndexName());
            symbolDetails.setCompanyName(symbolEntity.getCompanyName());
            symbolDetails.setIndustry(symbolEntity.getIndustry());
            symbolDetails.setSeries(symbolEntity.getSeries());
            symbolDetails.setIsInCode(symbolEntity.getIsInCode());
            symbolDetails.setExchange(symbolEntity.getExchange());
            symbolDetails.setCreatedAt(symbolEntity.getCreatedAt());
            symbolDetails.setUpdatedAt(symbolEntity.getUpdatedAt());
            symbolDetails.setScripCode(symbolEntity.getScripCode());

            return symbolDetails;
        } else {
            return null; // Symbol not found
        }
    }
}
