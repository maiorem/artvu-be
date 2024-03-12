package com.art.api.scheduler.application.jobconfig.detail;

import com.art.api.scheduler.application.apiResponse.KopisArtDetailResponse;
import com.art.api.scheduler.application.constants.Constants;
import com.art.api.scheduler.application.service.ArtDetailService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ArtDetailItemReader implements ItemReader<List<KopisArtDetailResponse>> {

    private final ArtDetailService artDetailService;

    @Value("${spring.open-api.secretKey}")
    private String secretKey;

    int count = 0;

    @Override
    public List<KopisArtDetailResponse> read() throws Exception {

        List<String> kopisArtIdList = artDetailService.artIdList();
        List<KopisArtDetailResponse> detailList = new ArrayList<>();
        Gson gson = new Gson();


        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                        .jaxb2Decoder(new Jaxb2XmlDecoder()))
                .build();

        WebClient webClient = WebClient.builder()
                .baseUrl(Constants.KOPIS_BASE_URL)
                .exchangeStrategies(exchangeStrategies)
                .build();

        for (String artId : kopisArtIdList) {
            Mono<String> response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/pblprfr")
                            .path("/"+artId)
                            .queryParam("service", secretKey)
                            .queryParam("newsql", "Y")
                            .build())
                    .accept(MediaType.APPLICATION_XML)
                    .retrieve()
                    .bodyToMono(String.class)
                    .log();
            String xmlResult = response.block();
            JSONObject jsonResult = XML.toJSONObject(xmlResult);
            KopisArtDetailResponse result = gson.fromJson(jsonResult.toString(), KopisArtDetailResponse.class);
            detailList.add(result);
        }

        count++;
        log.info("art DETAIL READER ============================================== ");
        log.info("art detail info ::" + detailList);

        return count > 1 ? null : detailList;
    }
}
