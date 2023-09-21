package com.project.crypto.controller;

import com.project.crypto.model.Interval;
import com.project.crypto.model.Kline;
import com.project.crypto.service.CryptoGetService;
import com.project.crypto.service.InputValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CryptoGetController {

    @Autowired
    private CryptoGetService cryptoService;

    @Autowired
    private InputValidationService validationService;

    @GetMapping("/klines")
    public List<Kline> klines(@RequestParam String symbol, @RequestParam Long startTime, @RequestParam Long endTime, @RequestParam String intervalStr) {
        validationService.validateSymbolFromCryptoDB(symbol);
        validationService.validateStartEndTime(startTime, endTime);
        Interval interval = validationService.convertStringToInterval(intervalStr);
        return cryptoService.getKlines(symbol, startTime, endTime, interval);
    }


    @PutMapping("/refreshSymbol")
    public void refresh() {
        validationService.refreshSymbol();
    }

}
