package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.application.openApiRecords.KopisArtListResponse;
import org.springframework.batch.item.ItemProcessor;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ArtListItemProcessor implements ItemProcessor<KopisArtListResponse, List<KopisArtListResponse>> {
    @Override
    public List<KopisArtListResponse> process(KopisArtListResponse item) throws Exception {
        LocalDate performStrDt = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate performEndDt = performStrDt.plusMonths(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

        String strDt = performStrDt.format(dateFormat);
        String endDt = performEndDt.format(dateFormat);





        return null;
    }
}
