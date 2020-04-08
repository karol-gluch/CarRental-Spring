package com.car.rental.project.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;


public class PayUConnection {
    public static final String CLIENT_ID ="383389";
    public static final String CLIENT_SECRET = "1b23ba89578d8f5c557792e58c777655";

    public String payUUrl(Order order) throws JsonProcessingException {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(getAccessToken());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        HttpEntity<?> entity = new HttpEntity<>(json,headers);
        String orderUrl = "https://secure.snd.payu.com/api/v2_1/orders/";
        ResponseEntity<String> response1 = client.exchange(orderUrl,HttpMethod.POST,entity,String.class);
        JsonNode root1 = mapper.readTree(response1.getBody());

        return root1.get("redirectUri").asText();

    }

    public String getAccessToken() throws JsonProcessingException {
        String tokenUrl = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize?grant_type=client_credentials&" + "client_id="
                + CLIENT_ID + "&client_secret="+CLIENT_SECRET;

        RestTemplate client = new RestTemplate();
        ResponseEntity<String> response = client.getForEntity(tokenUrl,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        return root.get("access_token").asText();
    }
}
