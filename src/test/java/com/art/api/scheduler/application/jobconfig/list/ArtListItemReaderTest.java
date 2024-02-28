package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.application.openApiRecords.KopisArtListResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
class ArtListItemReaderTest implements ItemReader<JSONObject> {

    private final WebClient webClient;

    private final String BASE_URL = "http://www.kopis.or.kr/openApi/restful/pblprfr";


    ArtListItemReaderTest(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public JSONObject read() {

        Mono<String> response = webClient.get()
                .uri(BASE_URL + "?" +
                        "service=9bc5a206710943769d657e163212035d" + "&" +
                        "stdate=20240201" + "&" +
                        "eddate=20240229" + "&" +
                        "cpage=1" + "&" +
                        "rows=10" + "&" +
                        "prstate=02" + "&" +
                        "newsql=Y")
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .log();

        ObjectMapper objectMapper = new ObjectMapper();
        String xmlResult = response.block();
        JSONObject result = XML.toJSONObject(xmlResult);
        System.out.println("result = " + result);

        return result;
    }

}