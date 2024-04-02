package com.art.api.artcrew.application;


import com.art.api.artcrew.domain.entity.ArtActors;
import com.art.api.artcrew.infrastructure.ArtActorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtActorsService {

    private final ArtActorsRepository actorRepository;

    //동명의 출연진 리스트
    public List<ArtActors> retrieveSameNameActorList(String name) {
        List<ArtActors> actorList = actorRepository.findAllByActorsNm(name);
        if (actorList == null) {

        }
        return actorList;
    }


    //출연진 detail
    public ArtActors retrieveActor(int actorId) {
        Optional<ArtActors> actor = actorRepository.findByArtActorsId(actorId);
        if (actor.isEmpty()) {

        }
        return actor.get();

    }

}
