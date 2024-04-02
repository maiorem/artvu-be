package com.art.api.facility.application;

import com.art.api.facility.domain.entity.ArtFacDetail;
import com.art.api.facility.infrastructure.ArtFacRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtFacService {

    private final ArtFacRepository artFacRepository;

    public ArtFacDetail retrieveFacility(String facId) {

        Optional<ArtFacDetail> byFacId = artFacRepository.findByArtFacId(facId);
        if(byFacId.isEmpty()) {

        }
        return byFacId.get();
    }


}
