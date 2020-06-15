package com.car.rental.project.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PayUTest {

    @Spy
    PayUConnection payUConnection;

    @Test
    void shouldReturnAccessToken() throws JsonProcessingException {
        String accessToken = "daJfjk30kdfkalbi31DFUDFI320kfSFk2";
        when(payUConnection.getAccessToken()).thenReturn(accessToken);
        String actualAccessToken = payUConnection.getAccessToken();
        assertEquals(accessToken,actualAccessToken);
    }

    @Test
    void shouldReturnRedirectUriToPayU() throws JsonProcessingException, URISyntaxException {
        Order order = new Order("1","Wypożyczenie 1","120",Stream.of(new Product("Samochód","120")).collect(Collectors.toList()));
        String expectedHost = "merch-prod.snd.payu.com";
        String redirectUri = payUConnection.payUUrl(order);
        URI uri = new URI(redirectUri);
        System.out.println(uri.getRawQuery());
        String host = uri.getHost();
        assertEquals(expectedHost,host);
    }

}