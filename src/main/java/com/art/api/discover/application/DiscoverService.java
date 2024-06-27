package com.art.api.discover.application;

import com.art.api.common.infrastructure.GenreRepository;
import com.art.api.discover.domain.dto.DiscoveryDTO;
import com.art.api.discover.infrastructure.DiscoverRepository;
import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.domain.entity.ClsCode;
import com.art.api.product.infrastructure.ArtGenreMppgRepository;
import com.art.api.product.infrastructure.ArtListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscoverService {

    private final DiscoverRepository discoverRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final GenreRepository genreRepository;

    private final ArtListRepository artListRepository;

    public Page<DiscoveryDTO> retrieveDiscovery(Pageable pageable) {
        Page<DiscoveryDTO> artMovies = convertDiscoveryDto(discoverRepository.retrieveDiscover(pageable));
        return artMovies;
    }

    public Page<DiscoveryDTO> convertDiscoveryDto(Page<DiscoveryDTO> artMovies){
        List<DiscoveryDTO> list = new ArrayList<>(artMovies.getContent());
        artMovies.stream().forEach(discoveryDTO -> {
            Optional<ArtList> optionalArtList = artListRepository.findByArtId(discoveryDTO.getArtId());
            Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(optionalArtList.get());
            mappingList.ifPresent(artGenreMppgs -> artGenreMppgs.forEach(genre -> discoveryDTO.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()))));
            discoveryDTO.setClsCode(String.valueOf(ClsCode.KOPIS));

        });

        // 순서 랜덤
        Collections.shuffle(list);
        int fromIndex = (int) artMovies.getPageable().getOffset();
        int toIndex = Math.min(fromIndex + artMovies.getPageable().getPageSize(), list.size());
        List<DiscoveryDTO> subList = list.subList(fromIndex, toIndex);

        return new PageImpl<>(subList, artMovies.getPageable(), artMovies.getTotalElements());
    }


}
