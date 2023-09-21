package com.project.crypto.service;

import com.project.crypto.exception.CryptoClientException;
import com.project.crypto.model.Kline;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Validated
public class BinanceApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${urlTemplate}")
    private String urlTemplate;
    @Value("${validationUrlTemplate}")
    private String validationUrlTemplate;

    public String exchangeInfo(@NotBlank String symbol) {
        String ResourceUrl = String.format(validationUrlTemplate, symbol);
        ResponseEntity<String> response;
        try{
            response = restTemplate.getForEntity(ResourceUrl, String.class);
            return response.getBody();
        }catch(HttpClientErrorException e){
            throw new CryptoClientException(e);
        }
    }

    public List<Kline> load(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime){
        String ResourceUrl = String.format(urlTemplate, symbol, startTime, endTime);
        ResponseEntity<String[][]> response = restTemplate.getForEntity(ResourceUrl, String[][].class);
        String[][] result=response.getBody();

        return Arrays.stream(result)
                .parallel()
                .map(row -> parseKlineObject(row, symbol))
                .toList();
    }
    private Kline parseKlineObject(String[] array, String symbol) {
        long openTime = Long.valueOf(array[0]);
        double openPrice = Double.valueOf(array[1]);
        double highPrice = Double.valueOf(array[2]);
        double lowPrice = Double.valueOf(array[3]);
        double closePrice = Double.valueOf(array[4]);
        double volume = Double.valueOf(array[5]);
        long closeTime = Long.valueOf(array[6]);
        double assetVolume = Double.valueOf(array[7]);
        long numberOfTrades = Long.valueOf(array[8]);
        double buyBaseAssetVolume = Double.valueOf(array[9]);
        double buyQuoteAssetVolume = Double.valueOf(array[10]);

        return new Kline(openTime, openPrice, highPrice, lowPrice, closePrice, volume, closeTime, assetVolume, numberOfTrades, buyBaseAssetVolume, buyQuoteAssetVolume, symbol);
    }
}
