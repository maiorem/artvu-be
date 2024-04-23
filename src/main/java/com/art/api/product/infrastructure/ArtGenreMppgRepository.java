package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.domain.entity.ArtGenreMppgId;
import com.art.api.product.domain.entity.ArtList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtGenreMppgRepository extends JpaRepository<ArtGenreMppg, ArtGenreMppgId> {

    Optional<List<ArtGenreMppg>> findAllByArtList(ArtList artList);

}
