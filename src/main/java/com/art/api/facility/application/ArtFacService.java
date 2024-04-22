package com.art.api.facility.application;

import com.art.api.core.exception.ItemNotFoundException;
import com.art.api.facility.domain.dto.SeatDTO;
import com.art.api.facility.domain.dto.TheaterDTO;
import com.art.api.facility.domain.entity.ArtFacDetail;
import com.art.api.facility.infrastructure.ArtFacRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtFacService {

    private final ArtFacRepository artFacRepository;

    public TheaterDTO retrieveFacility(String facId) {

        Optional<ArtFacDetail> byFacId = artFacRepository.findByArtFacId(facId);
        if(byFacId.isEmpty()) {
            return null;
        }
        return TheaterDTO.convertEntityToDto(byFacId.get());
    }

    public SeatDTO regreiveTip(String facId) {
        Optional<ArtFacDetail> byArtFacId = artFacRepository.findByArtFacId(facId);
        if(byArtFacId.isEmpty()) {
            return null;
        }
        return new SeatDTO(byArtFacId.get().getSeatImgUrl());

    }



}
