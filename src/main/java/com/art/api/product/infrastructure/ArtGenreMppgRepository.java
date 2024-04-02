package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.domain.entity.ArtGenreMppgId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtGenreMppgRepository extends JpaRepository<ArtGenreMppg, ArtGenreMppgId> {

    List<ArtGenreMppg> findAllByArtList(String artId);

}
