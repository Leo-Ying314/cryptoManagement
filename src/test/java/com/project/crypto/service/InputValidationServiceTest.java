package com.project.crypto.service;

import com.project.crypto.exception.InvalidInputException;
import com.project.crypto.model.Interval;
import com.project.crypto.repository.KlineMyBatisRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InputValidationServiceTest {
    @Mock
    private BinanceApiService binanceApiService;

    @Mock
    private KlineMyBatisRepository repository;

    @InjectMocks
    private InputValidationService service;


    @Test
    public void validateSymbolFromBinanceApi(){
        String symbol = "symbol";
        service.validateSymbolFromBinanceApi(symbol);
        verify(binanceApiService).exchangeInfo(symbol);
    }


    @Test
    public void getSymbolFromBinanceApiTest(){
        String symbol = "symbol";
        when(binanceApiService.exchangeInfo(symbol)).thenReturn(symbol);
        String res  = service.getSymbolFromBinanceApi(symbol);

        assertEquals(res, symbol);
    }

    @Test
    public void validateSymbolFromCryptoDBNegativeCase(){
        String symbol = "symbol";
        Set<String> mockSymbolsFromDB = mock(HashSet.class);
        service.symbolsFromDB = mockSymbolsFromDB;

        when(mockSymbolsFromDB.contains(symbol)).thenReturn(false);

        assertThrows(InvalidInputException.class, () -> {
            service.validateSymbolFromCryptoDB(symbol);
        });


    }

    @Test
    public void validateSymbolFromCryptoDBPositiveCase(){
        String symbol = "symbol";
        Set<String> mockSymbolsFromDB = mock(HashSet.class);
        service.symbolsFromDB = mockSymbolsFromDB;

        when(mockSymbolsFromDB.contains(symbol)).thenReturn(true);

        try{
            service.validateSymbolFromCryptoDB(symbol);
        }
        catch(Exception e){
            assertFalse(e instanceof InvalidInputException);
        }
    }

    @Test
    public void validateStartEndTimeNegativeCase(){
        Long startTime = 1L;
        Long endTime = 2L;

        try{
            service.validateStartEndTime(startTime, endTime);
        }
        catch(Exception e){
            assertFalse(e instanceof InvalidInputException);
        }
    }

    @Test
    public void validateStartEndTimePositiveCase(){
        Long startTime = 2L;
        Long endTime = 1L;

        try{
            service.validateStartEndTime(startTime, endTime);
        }
        catch(Exception e){
            assertEquals("Invalid Start Time. startTime="+startTime +" , endTime="+endTime, e.getMessage());
        }
    }

    @Test
    public void convertStringToIntervalTest(){
        Interval res = service.convertStringToInterval("1m");

        assertEquals(Interval.ONE_MINUTE, res);

        res = service.convertStringToInterval("5m");

        assertEquals(Interval.FIVE_MINUTES, res);

        res = service.convertStringToInterval("1h");

        assertEquals(Interval.ONE_HOUR, res);


        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> service.convertStringToInterval("3h"),
                "Expected doThing() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("unsupported interval"));

    }
}
