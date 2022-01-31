package com.lex.back;

import com.lex.back.controller.GenerateController;
import com.lex.back.model.dto.OriginalLinkDTO;
import com.lex.back.model.dto.ShortLinkDTO;
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
        OriginalLinkDTO originalLinkDTO = new OriginalLinkDTO("www.google.com");
        ShortLinkDTO shortLinkDTO = generateShortLink(originalLinkDTO);

        testRedirect(shortLinkDTO);
        testRedirect(shortLinkDTO);

        JSONObject statistic = getStatistic(shortLinkDTO);
        assertEquals(statistic.getInt("rank"), 1);
        assertEquals(statistic.getInt("count"), 2);
        assertEquals(statistic.getString("original"), originalLinkDTO.getOriginal());
    }

    private ShortLinkDTO generateShortLink(OriginalLinkDTO originalLinkDTO) {
        return webTestClient.post()
                .uri("generate")
                .bodyValue(originalLinkDTO)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ShortLinkDTO.class)
                .returnResult()
                .getResponseBody();
    }

    private void testRedirect(ShortLinkDTO shortLinkDTO) {
        webTestClient.get()
                .uri(shortLinkDTO.getLink())
                .exchange()
                .expectStatus()
                .is3xxRedirection();
    }

    private JSONObject getStatistic(ShortLinkDTO shortLinkDTO) throws JSONException {
        return new JSONObject(webTestClient.get()
                .uri("stats/" + extractShortLinkFromPrefix(shortLinkDTO.getLink()))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody());
    }

    private String extractShortLinkFromPrefix(String fullLink) {
        return fullLink.replace(GenerateController.SHORT_LINK_PREFIX, "");
    }
}