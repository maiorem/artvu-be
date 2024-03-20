package com.art.api.scheduler.application.service;

import com.art.api.scheduler.domain.entity.ArtDetailId;
import com.art.api.scheduler.domain.entity.KopisArtDetail;
import com.art.api.scheduler.domain.entity.KopisArtList;
import com.art.api.scheduler.infrastructure.repository.KopisArtDetailApiRepository;
import com.art.api.scheduler.infrastructure.repository.KopisArtListApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtDetailService {

    private final KopisArtListApiRepository listApiRepository;

    public List<String> artIdList() {

        List<KopisArtList> artList = listApiRepository.findAll();
        List<String> kopisArtIdList = new ArrayList<>();
        for (KopisArtList kopisArt : artList) {
            kopisArtIdList.add(kopisArt.getArtId());
        }
        return kopisArtIdList;
    }
}
