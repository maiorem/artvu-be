package com.art.api.common.infrastructure;

import com.art.api.common.domain.entity.ArtArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<ArtArea, Integer> {
    ArtArea findByAreaCode(String areaCode);
}
