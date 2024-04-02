package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtDetailRepository extends JpaRepository<ArtDetail, Integer> {

    Optional<ArtDetail> findByArtId(String artId);

}
