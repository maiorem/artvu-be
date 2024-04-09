package com.art.api.artcrew.infrastructure;

import com.art.api.artcrew.domain.entity.ArtActors;
import com.art.api.product.domain.entity.ArtList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtActorsRepository extends JpaRepository<ArtActors, Integer> {
    Optional<List<ArtActors>> findAllByActorsNm(String name);

    Optional<ArtActors> findByArtActorsId(int actorId);

    Optional<List<ArtActors>> findAllByArtList(ArtList artList);
}
