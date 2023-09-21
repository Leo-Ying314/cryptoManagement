package com.project.crypto.service;

import com.project.crypto.model.Kline;
import com.project.crypto.repository.KlineMyBatisRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.LongStream;

@Service
@Validated
public class CryptoLoadService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KlineMyBatisRepository klineRepository;

    @Autowired
    private BinanceApiService binanceApiService;


    @Value("${interval}")
    private Long interval;

//    public CryptoLoadService(KlineMyBatisRepository klineRepository, BinanceApiService binanceApiService, Long interval) {
//        this.klineRepository = klineRepository;
//        this.binanceApiService = binanceApiService;
//        this.interval = interval;
//    }

    public void loader(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime) {

        LongStream.range(startTime, endTime)
                .filter(s -> (s - startTime)%interval == 0)
                .parallel()
                .forEach(s -> {
                    long end = s + interval > endTime ? endTime : s + interval;
                    loader1(symbol, s, end);
                });
    }

    private void loader1(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime) {
        List<Kline> load = binanceApiService.load(symbol, startTime, endTime);
        klineRepository.insertBatch(load);
    }
}
