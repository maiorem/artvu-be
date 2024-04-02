package com.art.api.discover.application;

import com.art.api.common.infrastructure.GenreRepository;
import com.art.api.discover.domain.dto.DiscoveryDTO;
import com.art.api.discover.domain.entity.ArtMovie;
import com.art.api.discover.infrastructure.DiscoverRepository;
import com.art.api.product.domain.entity.ArtDetail;
import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.infrastructure.ArtDetailRepository;
import com.art.api.product.infrastructure.ArtGenreMppgRepository;
import com.art.api.product.infrastructure.ArtListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscoverService {

    private final DiscoverRepository discoverRepository;
    private final ArtListRepository artListRepository;
    private final ArtDetailRepository artDetailRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final GenreRepository genreRepository;

    public Page<DiscoveryDTO> retrieveDiscovery(Pageable pageable) {
        Page<DiscoveryDTO> artMovies = convertDiscoveryDto(discoverRepository.retrieveDiscover(pageable));
        return artMovies;
    }

    public Page<DiscoveryDTO> convertDiscoveryDto(Page<ArtMovie> artMovies){
        List<DiscoveryDTO> list = new ArrayList<>();
        artMovies.stream().forEach(movie -> {
            Optional<ArtDetail> artDetail = artDetailRepository.findByArtId(movie.getArtlist().getArtId());
            if (artDetail.isEmpty()) {

            }
            DiscoveryDTO dto = DiscoveryDTO.convertEntityToDto(movie.getArtlist(), artDetail.get(), movie);

            dto.setTotSize(artMovies.getSize());
            List<ArtGenreMppg> mappingList = mappRepository.findAllByArtList(movie.getArtlist().getArtId());

            mappingList.forEach( genre -> {
                dto.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()));
            });

            list.add(dto);

        });
        Page<DiscoveryDTO> result = new PageImpl<>(list, artMovies.getPageable(), artMovies.getSize());
        return result;
    }


}
