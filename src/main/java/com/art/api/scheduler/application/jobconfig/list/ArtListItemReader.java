package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.application.apiResponse.KopisArtListResponse;
import com.art.api.scheduler.application.constants.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ArtListItemReader implements ItemReader<KopisArtListResponse> {

    @Value("${spring.open-api.secretKey}")
    private String secretKey;

    int count = 0;

    @Override
    public KopisArtListResponse read() throws JsonProcessingException {

        String ROWS = "200";

        LocalDateTime performStrDt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime performEndDt = performStrDt.plusMonths(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

        String strDt = performStrDt.format(dateFormat);
        String endDt = performEndDt.format(dateFormat);

        Gson gson = new Gson();

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                        .jaxb2Decoder(new Jaxb2XmlDecoder()))
                .build();

        WebClient webClient = WebClient.builder()
                .baseUrl(Constants.KOPIS_BASE_URL)
                .exchangeStrategies(exchangeStrategies)
                .build();

        Mono<String> response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pblprfr")
                        .queryParam("service", secretKey)
                        .queryParam("stdate", strDt)
                        .queryParam("eddate", endDt)
                        .queryParam("cpage", "1")
                        .queryParam("rows", ROWS)
                        .queryParam("shcate", "AAAA")
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

        count++;
        log.info("art LIST READER ============================================== ");
        log.info("art list info ::"+result.toString());

        return count > 1 ? null : result;
    }
}
