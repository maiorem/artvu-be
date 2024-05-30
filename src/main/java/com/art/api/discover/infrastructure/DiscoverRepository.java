package com.art.api.discover.infrastructure;

import com.art.api.discover.domain.entity.ArtMovie;
import com.art.api.product.domain.entity.ArtList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscoverRepository extends JpaRepository<ArtMovie, Integer>, DiscoverRepositoryCustom {
    Optional<List<ArtMovie>> findAllByArtlist(ArtList artList);
}
