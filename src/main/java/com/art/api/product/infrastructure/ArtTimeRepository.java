package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.domain.entity.ArtTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtTimeRepository extends JpaRepository<ArtTime, Integer> {
    Optional<ArtTime> findByArtlist(ArtList artList);
}
