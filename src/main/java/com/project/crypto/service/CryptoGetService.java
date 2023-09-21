package com.project.crypto.service;

import com.project.crypto.model.Interval;
import com.project.crypto.model.Kline;
import com.project.crypto.repository.KlineMyBatisRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Validated
public class CryptoGetService {
    @Autowired
    private KlineMyBatisRepository repository;

    public List<Kline> getKlines(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime, @NotNull Interval interval) {
        List<Kline> dataArray = repository.getData(symbol, startTime, endTime);

        if(interval == Interval.ONE_MINUTE){
            return dataArray;
        }

        int num;
        if(interval == Interval.FIVE_MINUTES){
            num = 5;
        }else if(interval == Interval.ONE_HOUR){
            num = 60;
        }else{
            num = 1;
            throw new RuntimeException("unsupport interval");
        }

        return IntStream.range(0, dataArray.size())
                .parallel()
                .mapToObj(i -> {
                    int end = i+num >= dataArray.size() ? dataArray.size() : i+num;
                    return aggregate(dataArray, i ,end);
                })
                .toList();
    }

    private Kline aggregate( List<Kline> klines, int start, int end){
        Kline resultKline = new Kline();
        Kline firstKline = klines.get(start);
        resultKline.setOpenTime(firstKline.getOpenTime());
        resultKline.setOpenPrice(firstKline.getOpenPrice());
        resultKline.setHighPrice(firstKline.getHighPrice());
        resultKline.setLowPrice(firstKline.getLowPrice());
        resultKline.setClosePrice(firstKline.getClosePrice());
        resultKline.setVolume(0.0);
        resultKline.setCloseTime(firstKline.getCloseTime());
        resultKline.setAssetVolume(0.0);
        resultKline.setNumberOfTrades(0L);
        resultKline.setBuyBaseAssetVolume(0.0);
        resultKline.setBuyQuoteAssetVolume(0.0);
        resultKline.setSymbol(firstKline.getSymbol());

        for(int i = start; i < end; i++){
            Kline curRawKline = klines.get(i);

            resultKline.setHighPrice(Math.max(curRawKline.getHighPrice(), resultKline.getHighPrice()));
            resultKline.setLowPrice(Math.min(curRawKline.getLowPrice(), resultKline.getLowPrice()));

            resultKline.setClosePrice(curRawKline.getClosePrice());
            resultKline.setCloseTime(curRawKline.getCloseTime());

            double newVolume = curRawKline.getVolume() + resultKline.getVolume();
            resultKline.setVolume(newVolume);

            double newAssetVolume = curRawKline.getAssetVolume() + resultKline.getAssetVolume();
            resultKline.setAssetVolume(newAssetVolume);

            long newNumberOfTrades = curRawKline.getNumberOfTrades() + resultKline.getNumberOfTrades();
            resultKline.setNumberOfTrades(newNumberOfTrades);

            double newBaseVolume = curRawKline.getBuyBaseAssetVolume() + resultKline.getBuyBaseAssetVolume();
            resultKline.setBuyBaseAssetVolume(newBaseVolume);

            double newQuoteVolume = curRawKline.getBuyQuoteAssetVolume() + resultKline.getBuyQuoteAssetVolume();
            resultKline.setBuyQuoteAssetVolume(newQuoteVolume);
        }
        return resultKline;
    }




}
