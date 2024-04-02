package com.art.api.artcrew.infrastructure;

import com.art.api.artcrew.domain.entity.ArtActors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtActorsRepository extends JpaRepository<ArtActors, Integer> {
    List<ArtActors> findAllByActorsNm(String name);

    Optional<ArtActors> findByArtActorsId(int actorId);
}
