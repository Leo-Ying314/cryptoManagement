package com.project.crypto.controller;

import com.project.crypto.service.CryptoLoadService;
import com.project.crypto.service.InputValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CryptoLoadController {

    @Autowired
    private CryptoLoadService cryptoService;

    @Autowired
    private InputValidationService validationService;

    @GetMapping("/crypto")
    public void crypto(@RequestParam String symbol, @RequestParam Long startTime, @RequestParam Long endTime) {
        validationService.validateSymbolFromBinanceApi(symbol);
        validationService.validateStartEndTime(startTime, endTime);
        cryptoService.loader(symbol, startTime, endTime);
    }
}
