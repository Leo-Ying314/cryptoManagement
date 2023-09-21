package com.project.crypto.service;

import com.project.crypto.exception.InvalidInputException;
import com.project.crypto.model.Interval;
import com.project.crypto.repository.KlineMyBatisRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Service
@Validated
public class InputValidationService {

    @Autowired
    private BinanceApiService binanceApiService;

    @Autowired
    private KlineMyBatisRepository repository;

    public Set<String> symbolsFromDB;

    @PostConstruct
    public void postConstruct(){
        refreshSymbol();
    }

    public void refreshSymbol(){
        symbolsFromDB =  new HashSet<>(repository.findAllSymbols());
    }

    public void validateSymbolFromBinanceApi(@NotBlank String symbol) {
        binanceApiService.exchangeInfo(symbol);
    }

    public String getSymbolFromBinanceApi(@NotBlank String symbol) {
        return binanceApiService.exchangeInfo(symbol);
    }


    public void validateSymbolFromCryptoDB(@NotBlank String symbol){
        if(!symbolsFromDB.contains(symbol)){
            throw new InvalidInputException("Symbol:" + symbol+" Not Found.");
        }
    }

        public void validateStartEndTime(@NotNull Long startTime, @NotNull Long endTime){
            if(startTime >= endTime){
                throw new InvalidInputException("Invalid Start Time. startTime="+startTime +" , endTime="+endTime);
            }
        }

    public Interval convertStringToInterval(String intervalStr){
        if (intervalStr.equals("1m")){
            return Interval.ONE_MINUTE;
        }
        if (intervalStr.equals("5m")){
            return Interval.FIVE_MINUTES;
        }
        if (intervalStr.equals("1h")){
            return Interval.ONE_HOUR;
        }
        throw new InvalidInputException("unsupported interval: "+ intervalStr);
    }
}
