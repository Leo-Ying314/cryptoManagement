package com.project.crypto.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BinanceApiServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BinanceApiService service;

    @Test
    public void exchangeInfoTest() {
        String url = "url";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        when(restTemplate.getForEntity(url, String.class))
                .thenReturn(response);
        String actual = service.exchangeInfo(url);
        assertEquals(url, actual);
    }

    @Test
    public void loadTest(){
        String url = "url";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        when(restTemplate.getForEntity(url, String.class))
                .thenReturn(response);
        String actual = service.exchangeInfo(url);
        assertEquals(url, actual);
    }

}
