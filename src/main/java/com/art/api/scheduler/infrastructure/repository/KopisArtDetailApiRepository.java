package com.art.api.scheduler.infrastructure.repository;

import com.art.api.scheduler.domain.entity.ArtDetailId;
import com.art.api.scheduler.domain.entity.ArtId;
import com.art.api.scheduler.domain.entity.KopisArtDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KopisArtDetailApiRepository extends JpaRepository<KopisArtDetail, ArtDetailId> {
    KopisArtDetail findByArtId(String artId);
}
