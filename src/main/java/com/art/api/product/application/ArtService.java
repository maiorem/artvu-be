package com.art.api.product.application;


import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.infrastructure.ArtListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtService {

    private final ArtListRepository artListRepository;

    public Page<ArtListDTO> retrieveArtList(Pageable pageable, String genre, String local, String search) {
        Page<ArtListDTO> artList = artListRepository.findSearchResult(pageable, genre, local, search);
        return artList;
    }

}
