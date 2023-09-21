package com.project.crypto.service;

import com.project.crypto.model.Kline;
import com.project.crypto.repository.KlineMyBatisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CryptoGetServiceTest {
    @Mock
    private KlineMyBatisRepository repository;

    @InjectMocks
    private CryptoGetService service;

    @Test
    public void getKlinesTest(){

    }
}
