package com.springboot.evaluationtask.DashboardModule.repository;

import com.springboot.evaluationtask.DashboardModule.enity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SymbolRepository extends JpaRepository<Symbol,Long> {


    boolean existsBySymbol(String symbol);

    Optional<Symbol> findBySymbol(String symbol);
}
