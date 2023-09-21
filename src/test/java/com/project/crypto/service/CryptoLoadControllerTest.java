package com.project.crypto.service;

import com.project.crypto.controller.CryptoGetController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CryptoLoadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CryptoLoadService cryptoLoadService;

    @Mock
    private InputValidationService validationService;

    @InjectMocks
    private CryptoGetController controller;

//    @Test
//        public void testCrypto() throws Exception {
//            mockMvc.perform(get("/crypto")
//                            .param("symbol", "BTCUSDT")
//                            .param("startTime", "1600000000")
//                            .param("endTime", "1610000000"))
//                    .andExpect(status().isOk());
//
//            verify(cryptoLoadService).loader(anyString(), anyLong(), anyLong());
//            verify(validationService).validateSymbolFromBinanceApi(anyString());
//            verify(validationService).validateStartEndTime(anyLong(), anyLong());
//        }
}
