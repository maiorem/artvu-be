package com.art.api.discover.infrastructure;

import com.art.api.discover.domain.entity.ArtMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoverRepository extends JpaRepository<ArtMovie, Integer>, DiscoverRepositoryCustom {
}
