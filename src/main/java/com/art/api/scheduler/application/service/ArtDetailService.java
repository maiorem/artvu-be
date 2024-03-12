package com.art.api.scheduler.application.service;

import com.art.api.scheduler.domain.entity.ArtId;
import com.art.api.scheduler.domain.entity.KopisArtDetail;
import com.art.api.scheduler.domain.entity.KopisArtList;
import com.art.api.scheduler.infrastructure.repository.KopisArtDetailApiRepository;
import com.art.api.scheduler.infrastructure.repository.KopisArtListApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtDetailService {


    private final KopisArtListApiRepository listApiRepository;

    private final KopisArtDetailApiRepository detailApiRepository;

    public List<String> artIdList() {

        List<KopisArtList> artList = listApiRepository.findAll();
        List<String> kopisArtIdList = new ArrayList<>();
        for (KopisArtList kopisArtList : artList) {
            ArtId artId = ArtId.builder()
                    .artId(kopisArtList.getArtId())
                    .regDt(kopisArtList.getRegDt())
                    .build();
            Optional<KopisArtDetail> art = detailApiRepository.findById(artId);
            kopisArtIdList.add(art.get().getArtId());
        }
        return kopisArtIdList;
    }
}
