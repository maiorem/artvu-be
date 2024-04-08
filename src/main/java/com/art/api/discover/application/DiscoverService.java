package com.art.api.discover.application;

import com.art.api.common.infrastructure.GenreRepository;
import com.art.api.discover.domain.dto.DiscoveryDTO;
import com.art.api.discover.domain.entity.ArtMovie;
import com.art.api.discover.infrastructure.DiscoverRepository;
import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.infrastructure.ArtDetailRepository;
import com.art.api.product.infrastructure.ArtGenreMppgRepository;
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
    private final ArtDetailRepository artDetailRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final GenreRepository genreRepository;

    public Page<DiscoveryDTO> retrieveDiscovery(Pageable pageable) {
        Page<DiscoveryDTO> artMovies = convertDiscoveryDto(discoverRepository.retrieveDiscover(pageable), pageable);
        return artMovies;
    }

    public Page<DiscoveryDTO> convertDiscoveryDto(Page<ArtMovie> artMovies, Pageable pageable){
        List<DiscoveryDTO> list = new ArrayList<>();
        artMovies.stream().forEach(movie -> {
            DiscoveryDTO dto = DiscoveryDTO.convertEntityToDto(movie.getArtlist(), movie);

            dto.setTotSize(artMovies.getTotalPages());
            Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(movie.getArtlist().getArtId());
            mappingList.ifPresent(artGenreMppgs -> artGenreMppgs.forEach(genre -> dto.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()))));
            list.add(dto);

        });
        // 순서 랜덤
        Collections.shuffle(list);
        int fromIndex = (int) pageable.getOffset();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), list.size());
        List<DiscoveryDTO> subList = list.subList(fromIndex, toIndex);

        Page<DiscoveryDTO> result = new PageImpl<>(subList, artMovies.getPageable(), artMovies.getSize());
        return result;
    }


}
