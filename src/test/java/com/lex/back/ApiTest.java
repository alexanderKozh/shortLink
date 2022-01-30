
package com.lex.back;

import com.lex.back.controller.GenerateController;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() throws JSONException {
        String original = "www.google.com";
        String link = generateShortLink(original);

        testRedirect(link);
        testRedirect(link);

        JSONObject statistic = getStatistic(link.replace(GenerateController.SHORT_LINK_PREFIX, ""));
        assertEquals(statistic.getInt("rank"), 1);
        assertEquals(statistic.getInt("count"), 2);
        assertEquals(statistic.getString("original"), original);
    }

    private String generateShortLink(String original) throws JSONException {
        String generatedLink = webTestClient.post()
                .uri(
                        uriBuilder -> uriBuilder.path("generate")
                                .queryParam("original", original)
                                .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        return new JSONObject(generatedLink).getString("link");
    }

    private void testRedirect(String link) {
        webTestClient.get()
                .uri(link)
                .exchange()
                .expectStatus()
                .is3xxRedirection();
    }

    private JSONObject getStatistic(String shortName) throws JSONException {
        return new JSONObject(webTestClient.get()
                .uri("stats/" + shortName)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody());
    }
}