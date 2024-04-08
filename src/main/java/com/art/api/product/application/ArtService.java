package com.art.api.product.application;


import com.art.api.common.domain.entity.ArtArea;
import com.art.api.common.infrastructure.GenreRepository;
import com.art.api.common.infrastructure.LocalRepository;
import com.art.api.facility.infrastructure.ArtFacRepository;
import com.art.api.product.domain.dto.ArtDetailDTO;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.entity.*;
import com.art.api.product.infrastructure.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtService {

    private final ArtListRepository artListRepository;
    private final ArtDetailRepository detailRepository;
    private final LocalRepository areaRepository;
    private final GenreRepository genreRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final ArtImgRepository imgListRepository;
    private final ArtTimeRepository timeRepository;
    private final ArtFacRepository facilityRepository;
    public Page<ArtListDTO> retrieveArtList(Pageable pageable, String genre, String local, String search) {
        Page<ArtListDTO> artList = convertArtList(artListRepository.findSearchResult(pageable, genre, local, search));
        return artList;
    }

    private Page<ArtListDTO> convertArtList(Page<ArtList> artList) {
        List<ArtListDTO> list = new ArrayList<>();
        artList.stream().forEach(item -> {

            ArtListDTO dto = ArtListDTO.convertEntityToDto(item);
            ArtArea area = areaRepository.findByAreaCode(item.getAreaCode().getAreaCode());
            Optional<List<ArtImg>> artImgList = imgListRepository.findAllByArtList(item);
            String posterUrl = "";
            if(artImgList.isPresent()) {
                posterUrl = artImgList.get().stream().filter(n -> n.getClsCode().equals(ClsCode.POSTER)).findAny().orElse(ArtImg.builder().imgUrl("").build()).getImgUrl();
            }
            Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(item.getArtId());

            dto.setArea(area.getAreaNm());
            dto.setPosterUrl(posterUrl);
            mappingList.ifPresent(artGenreMppgs -> {
                for (ArtGenreMppg genre : artGenreMppgs) {
                    dto.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()));
                }
            });
            list.add(dto);
        });


        Page<ArtListDTO> result = new PageImpl<>(list, artList.getPageable(), artList.getSize());
        return result;
    }

    public ArtDetailDTO retrieveArtDetail(String artId) {
        Optional<ArtList> art = artListRepository.findByArtId(artId);
        if(art.isEmpty()) {

        }
        Optional<ArtDetail> detail = detailRepository.findByArtId(artId);
        if (detail.isEmpty()) {

        }
        Optional<ArtTime> time = timeRepository.findByArtlist(art.get());
        if (time.isEmpty()) {

        }
        ArtDetailDTO dto = ArtDetailDTO.convertEntityToDto(art.get(), detail.get(), time.get(), art.get().getArtFacId());

        Optional<List<ArtImg>> artImgList = imgListRepository.findAllByArtList(art.get());
        artImgList.ifPresent(dto::setArtImgList);
        Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(art.get().getArtId());
        mappingList.ifPresent(artGenreMppgs -> {
            for (ArtGenreMppg genre : artGenreMppgs) {
                dto.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()));
            }
        });
        return dto;
    }



}
