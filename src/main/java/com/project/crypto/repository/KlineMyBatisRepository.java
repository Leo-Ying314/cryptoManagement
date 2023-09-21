package com.project.crypto.repository;

import com.project.crypto.model.Kline;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.apache.ibatis.annotations.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Mapper
@Validated
public interface KlineMyBatisRepository {
    @Select("select * from kline")
    public @NotEmpty List<@Valid Kline> findAll();

    @Insert({
            "<script>",
            "insert into kline (open_time, open_price, high_price, low_price, close_price, volume, close_time, asset_volume, number_of_trades, buy_base_asset_volume, buy_quote_asset_volume, symbol)",
            "values ",
            "<foreach collection='klineList' item='kline' separator=','>",
            "( #{kline.openTime}, #{kline.openPrice}, #{kline.highPrice}, #{kline.lowPrice}, #{kline.closePrice}, #{kline.volume}, #{kline.closeTime}, #{kline.assetVolume}, #{kline.numberOfTrades}, #{kline.buyBaseAssetVolume}, #{kline.buyQuoteAssetVolume}, #{kline.symbol})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(@Param("klineList") List<Kline> klineList);

    @Select("select distinct symbol from kline")
    public List<String> findAllSymbols();

    @Select("select distinct * from crypto.kline where symbol = #{symbol} and open_time >= #{startTime} and close_time <= #{closeTime} order by open_time asc")
    @Results(value = {
            @Result(property = "symbol", javaType = String.class, column = "symbol"),
            @Result(property = "openTime",javaType = Long.class, column = "open_time"),
            @Result(property = "openPrice", javaType = Double.class, column = "open_price"),
            @Result(property = "highPrice", javaType = Double.class, column = "high_price"),
            @Result(property = "lowPrice", javaType = Double.class, column = "low_price"),
            @Result(property = "closePrice", javaType = Double.class, column = "close_price"),
            @Result(property = "volume", javaType = Double.class, column = "volume"),
            @Result(property = "closeTime", javaType = Long.class, column = "close_time"),
            @Result(property = "assetVolume", javaType = Double.class, column = "asset_volume"),
            @Result(property = "numberOfTrades", javaType = Long.class, column = "number_of_trades"),
            @Result(property = "buyBaseAssetVolume", javaType = Double.class, column = "buy_base_asset_volume"),
            @Result(property = "buyQuoteAssetVolume", javaType = Double.class, column = "buy_quote_asset_volume"),
    })
    public @NotEmpty List<Kline> getData(@Param("symbol") String symbol, @Param("startTime") Long startTime, @Param("closeTime") Long closeTime);}

