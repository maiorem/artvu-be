package com.art.api.scheduler.application.jobconfig.list;

import org.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.json.XML;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ArtListItemReader implements ItemReader<JSONObject> {

    private final WebClient webClient;

    private final String BASE_URL = "";

    @Override
    public JSONObject read() {

        Mono<String> response = webClient.get()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .log();

        String xmlResult = response.block();
        JSONObject result = XML.toJSONObject(xmlResult);

        return result;
    }
}
