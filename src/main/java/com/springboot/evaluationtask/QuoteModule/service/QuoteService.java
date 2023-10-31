package com.springboot.evaluationtask.QuoteModule.service;

import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuoteService {
     Symbol saveSymbol(Symbol symbol);
    Symbol getSymbolById(Long symbolId);
    List<Symbol> uploadSymbolDetails(MultipartFile file);
}
