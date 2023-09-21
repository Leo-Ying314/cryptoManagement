package com.project.crypto.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class Kline {

    @NotNull
    private Long openTime;
    @Min(0)
    private Double openPrice;
    private Double highPrice;
    private Double lowPrice;
    private Double closePrice;
    private Double volume;
    private Long closeTime;
    private Double assetVolume;
    private Long numberOfTrades;
    private Double buyBaseAssetVolume;
    private Double buyQuoteAssetVolume;
    private String symbol;

    public Kline(Long openTime, Double openPrice, Double highPrice, Double lowPrice, Double closePrice, Double volume, Long closeTime, Double assetVolume, Long numberOfTrades, Double buyBaseAssetVolume, Double buyQuoteAssetVolume, String symbol) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.closeTime = closeTime;
        this.assetVolume = assetVolume;
        this.numberOfTrades = numberOfTrades;
        this.buyBaseAssetVolume = buyBaseAssetVolume;
        this.buyQuoteAssetVolume = buyQuoteAssetVolume;
        this.symbol = symbol;
    }
}
