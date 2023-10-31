package com.springboot.evaluationtask.QuoteModule.controller;

import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import com.springboot.evaluationtask.QuoteModule.service.Impl.QuoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/quote")
public class QuoteController {
    @Autowired
    private QuoteServiceImpl symbolService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSymbolDetails(@RequestParam("file") MultipartFile file) {
        List<Symbol> uploadedSymbols = symbolService.uploadSymbolDetails(file);

        if (!uploadedSymbols.isEmpty()) {
            return ResponseEntity.ok("Symbols uploaded successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading symbols or no valid symbols found in the file.");
        }
    }

    @GetMapping("/{symbolId}")
    public ResponseEntity<Symbol> getSymbolDetails(@PathVariable Long symbolId) {
        Symbol symbol = symbolService.getSymbolById(symbolId);

        if (symbol != null) {
            return ResponseEntity.ok(symbol);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
