package com.art.api.product.application;


import com.art.api.common.domain.entity.ArtArea;
import com.art.api.common.infrastructure.GenreRepository;
import com.art.api.common.infrastructure.LocalRepository;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.domain.entity.ArtImg;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.infrastructure.ArtGenreMppgRepository;
import com.art.api.product.infrastructure.ArtImgRepository;
import com.art.api.product.infrastructure.ArtListRepository;
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
    private final LocalRepository areaRepository;
    private final GenreRepository genreRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final ArtImgRepository artImgListRepository;


    public Page<ArtListDTO> retrieveArtList(Pageable pageable, String genre, String local, String search) {
        Page<ArtListDTO> artList = convertArtList(artListRepository.findSearchResult(pageable, genre, local, search));
        return artList;
    }

    private Page<ArtListDTO> convertArtList(Page<ArtList> artList) {
        List<ArtListDTO> list = new ArrayList<>();
        artList.stream().forEach(item -> {

            ArtListDTO dto = ArtListDTO.convertEntityToDto(item);
            ArtArea area = areaRepository.findByAreaCode(item.getAreaCode().getAreaCode());
            Optional<List<ArtImg>> artImgList = artImgListRepository.findAllByArtList(item);
            String posterUrl = "";
            if(artImgList.isPresent()) {
                posterUrl = artImgList.get().stream().filter(n -> n.getClsCode().equals("P")).findAny().orElse(ArtImg.builder().imgUrl("").build()).getImgUrl();
            }
            Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(item);

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



}
