package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.application.openApiRecords.KopisArtListResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ArtListItemReader implements ItemReader<KopisArtListResponse> {

    @Value("${spring.open-api.secretKey}")
    private String secretKey;


    @Override
    public KopisArtListResponse read() throws JsonProcessingException {

        String BASE_URL = "http://www.kopis.or.kr/openApi/restful/pblprfr";
        String ROWS = "200";

        LocalDate performStrDt = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate performEndDt = performStrDt.plusMonths(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

        String strDt = performStrDt.format(dateFormat);
        String endDt = performEndDt.format(dateFormat);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                        .jaxb2Decoder(new Jaxb2XmlDecoder()))
                .build();

        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .exchangeStrategies(exchangeStrategies)
                .build();

        Mono<String> response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
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
        ObjectMapper xmlMapper = new XmlMapper();
        KopisArtListResponse result = xmlMapper.readValue(xmlResult, KopisArtListResponse.class);

        return result;
    }
}
