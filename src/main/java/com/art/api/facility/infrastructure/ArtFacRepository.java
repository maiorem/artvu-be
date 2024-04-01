package com.art.api.facility.infrastructure;

import com.art.api.facility.domain.entity.ArtFacDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtFacRepository extends JpaRepository<ArtFacDetail, Integer> {
    Optional<ArtFacDetail> findByFacId(int facId);


}
