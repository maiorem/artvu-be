package com.art.api.artcrew.application;


import com.art.api.artcrew.domain.dto.PeopleDTO;
import com.art.api.artcrew.domain.entity.ArtActors;
import com.art.api.artcrew.infrastructure.ArtActorsRepository;
import com.art.api.core.exception.ItemNotFoundException;
import com.art.api.product.domain.entity.ArtDetail;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.infrastructure.ArtDetailRepository;
import com.art.api.product.infrastructure.ArtListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtActorsService {

    private final ArtActorsRepository actorRepository;
    private final ArtDetailRepository detailRepository;
    private final ArtListRepository listRepository;

    //동명의 출연진 리스트
    public List<ArtActors> retrieveSameNameActorList(String name) {
        Optional<List<ArtActors>> actorList = actorRepository.findAllByActorsNm(name);
        if (actorList.isEmpty()) {
            return null;
        }
        return actorList.get();
    }


    //출연진 detail
    public ArtActors retrieveActor(int actorId) {
        Optional<ArtActors> actor = actorRepository.findByArtActorsId(actorId);
        if (actor.isEmpty()) {
            return null;
        }
        return actor.get();

    }

    //제작/기획/출연진
    public PeopleDTO retrievePeople(String artId) {
        Optional<ArtDetail> byArtId = detailRepository.findByArtId(artId);
        if (byArtId.isEmpty()) {
            return null;
        }
        PeopleDTO dto = PeopleDTO.convertEntityToDto(byArtId.get());
        return dto;
    }

}
