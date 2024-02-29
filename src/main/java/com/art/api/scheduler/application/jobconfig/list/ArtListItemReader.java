package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.application.openApiRecords.KopisArtListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.json.XML;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ArtListItemReader implements ItemReader<KopisArtListResponse> {

    private final WebClient webClient;

    @Value("${spring.open-api.secretKey}")
    private final String secretKey;

    private final String BASE_URL = "http://www.kopis.or.kr/openApi/restful/pblprfr";

    private final String ROWS = "30";

    @Override
    public KopisArtListResponse read() {

        LocalDate performStrDt = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate performEndDt = performStrDt.plusMonths(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

        String strDt = performStrDt.format(dateFormat);
        String endDt = performEndDt.format(dateFormat);

        Gson gson = new Gson();
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("service", secretKey)
                        .queryParam("stdate", strDt)
                        .queryParam("eddate", endDt)
                        .queryParam("cpage", "1")
                        .queryParam("rows", ROWS)
                        .queryParam("prstate", "02")
                        .queryParam("newsql", "Y")
                        .build())
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .log();

        String xmlResult = response.block();
        JSONObject jsonResult = XML.toJSONObject(xmlResult);
        KopisArtListResponse result = gson.fromJson(jsonResult.toString(), KopisArtListResponse.class);

        return result;
    }
}
