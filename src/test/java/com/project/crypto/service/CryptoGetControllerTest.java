package com.project.crypto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crypto.controller.CryptoGetController;
import com.project.crypto.model.Kline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CryptoGetControllerTest {


    @Mock
    private CryptoGetService cryptoGetService;

    @Mock
    private InputValidationService validationService;

    @InjectMocks
    private CryptoGetController controller;

    @Test
    public void shouldReturnExpectedKlines() {
        List<Kline> expectedKlines = new ArrayList<>();
        when(cryptoGetService.getKlines(anyString(), anyLong(), anyLong(), any())).thenReturn(expectedKlines);

        verify(cryptoGetService).getKlines(anyString(), anyLong(), anyLong(), any());
        verify(validationService).validateSymbolFromCryptoDB(anyString());
    }

}
