package com.car.rental.project.social;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FbTest {

    @Mock
    FBGraph fbGraph;

    @Spy
    FBConnection fbConnection;

    @Test
    public void shouldCreateCorrectFbAuthUrl() {
        final String FB_APP_ID ="228175701665524";
        final String REDIRECT_URI = "http://localhost:9090/fblogin";
        String authURL = "http://www.facebook.com/dialog/oauth?" + "client_id="
                + FB_APP_ID + "&redirect_uri="
                + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)
                + "&scope=email";

        assertEquals(authURL,fbConnection.getFBAuthUrl());
    }

    /*@Test
    public void shouldReturnAccessToken(){
        String accessToken = "EAADPhkuk5vQBAJTHqI1eUkZA9XgfjBG9DibLBJjpIKWZBVyzZA09cn74y5QZCyUfBDJzWhXOx6z4ZC8r1vbVXJRSGg1aJDc4ZAE3wn7J5VNBeu2FW2Ga42bhQA6OuRQMc0O6ZABOCURXanvo96dP5ZA8fJWqwEb4jvZCcQo8e99R23gZDZD";
        String code = "AQAGqyLQyGYtDY2vYJHhFyBpJYi374pbptDikXSF2X-MuYQmt0KhfAnvzdVUFkhSCtqusPS05dG4JQSPAIYFcK4Y0jRH05LrZYimOEfXIOEXwtRjCBiWYoj6NbN72dCzex59q3kPak5p_tVTHiY40rKXgDK45pID8Hlrcf0ozqm8MUXwd2pt35oBJP_ATK02Q3Oe0VqWyLBVACFB2MJPQuYrPcR97hyhKtlwFqyS7sQBRh2urfar39seQkhyllqTJf3iJxR9ZQFRlcVUeKvSe7fJkdlAanPFCffXRtbQXmDxI7lmBglMKOvB7oU5cflskqZYF_JQbhRY2QBJWJA0wEs7";
        when(fbConnection.getAccessToken(code)).thenReturn(accessToken);

        String actualToken = fbConnection.getAccessToken(code);
        assertEquals(accessToken,actualToken);
    }*/
}