package com.art.api.facility.application;

import com.art.api.facility.domain.dto.SeatDTO;
import com.art.api.facility.domain.dto.TheaterDTO;
import com.art.api.facility.domain.entity.ArtFacDetail;
import com.art.api.facility.infrastructure.ArtFacRepository;
import com.art.api.product.domain.entity.ArtImg;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.domain.entity.ClsCode;
import com.art.api.product.infrastructure.ArtImgRepository;
import com.art.api.product.infrastructure.ArtListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtFacService {


    private final ArtListRepository artRepository;
    private final ArtFacRepository artFacRepository;
    private final ArtImgRepository imgRepository;

    public TheaterDTO retrieveFacility(String facId) {

        Optional<ArtFacDetail> byFacId = artFacRepository.findByArtFacId(facId);
        if(byFacId.isEmpty()) {

        }
        return TheaterDTO.convertEntityToDto(byFacId.get());
    }

    public SeatDTO regreiveTip(String artId) {
        Optional<ArtList> art = artRepository.findByArtId(artId);
        if(art.isEmpty()) {

        }
        Optional<List<ArtImg>> imgList = imgRepository.findAllByArtList(art.get());
        if(imgList.isEmpty()) {

        }
        for (ArtImg artImg : imgList.get()) {
            if(artImg.getClsCode().equals(ClsCode.SEAT)) {
               return new SeatDTO(artImg.getImgUrl());
            }
        }
        return null;

    }



}
