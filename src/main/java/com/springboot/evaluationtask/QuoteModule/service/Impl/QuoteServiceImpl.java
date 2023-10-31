package com.springboot.evaluationtask.QuoteModule.service.Impl;


import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.DashboardModule.repository.SymbolRepository;
import com.springboot.evaluationtask.QuoteModule.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

        @Autowired
        private SymbolRepository symbolRepository;
    public Symbol saveSymbol(Symbol symbol) {
        return symbolRepository.save(symbol);
    }


        public Symbol getSymbolById(Long symbolId) {
            return symbolRepository.findById(symbolId).orElse(null);
        }

        public List<Symbol> uploadSymbolDetails(MultipartFile file) {
            List<Symbol> symbols = new ArrayList<>();

            try {
                InputStream is = file.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                // Skip the header row
                br.readLine();

                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 11) {
                        Symbol symbol = new Symbol();
                        symbol.setSymbol(parts[0]);
                        symbol.setSymbolName(parts[1]);
                        symbol.setIndexName(parts[2]);
                        symbol.setCompanyName(parts[3]);
                        symbol.setIndustry(parts[4]);
                        symbol.setSeries(parts[5]);
                        symbol.setIsInCode(parts[6]);
                        symbol.setExchange(parts[7]);
                        symbol.setCreatedAt(parts[8]);
                        symbol.setUpdatedAt(parts[9]);
                        symbol.setScripCode(parts[10]);

                        symbols.add(saveSymbol(symbol));
                    }
                }

                br.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException("Error parsing file: " + e.getMessage());
            }

            return symbols;
        }
}
