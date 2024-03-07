package com.art.api.scheduler.application.facade;

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
public class ArtDetailItemFacade {

    private final KopisArtListApiRepository listApiRepository;

    private final KopisArtDetailApiRepository detailApiRepository;

    public List<String> retrieveArtIdList() {
        List<KopisArtList> artList = listApiRepository.findAll();
        List<String> results = new ArrayList<>();
        for (KopisArtList kopisArtList : artList) {
            ArtId artId = ArtId.builder()
                            .artId(kopisArtList.getArtId())
                            .regDt(kopisArtList.getRegDt())
                            .build();
            Optional<KopisArtDetail> art = detailApiRepository.findById(artId);
            results.add(art.get().getArtId());
        }

        return results;
    }


}
