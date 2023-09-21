package com.project.crypto.service;

import com.project.crypto.model.Kline;
import com.project.crypto.repository.KlineMyBatisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoLoadServiceTest {
    @Mock
    private KlineMyBatisRepository repository;

    @Mock
    private BinanceApiService binanceApiService;

    @InjectMocks
    private CryptoLoadService service;

    //@Test
    public void loaderTestPositiveCase(){
        String symbol = "symbol";
        Long startTime = 1L;
        Long endTime = 2L;
//        Long interval = 1L;

        List<Kline> klines = Arrays.asList(new Kline(), new Kline());
        when(binanceApiService.load(anyString(), anyLong(), anyLong())).thenReturn(klines);

        service.loader(symbol, startTime,endTime);
        verify(repository).insertBatch(klines);
        verify(binanceApiService).load(symbol, startTime,endTime);
    }
}
