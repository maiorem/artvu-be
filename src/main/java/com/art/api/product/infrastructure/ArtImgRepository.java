package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtImg;
import com.art.api.product.domain.entity.ArtList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtImgRepository extends JpaRepository<ArtImg, Integer> {
    Optional<List<ArtImg>> findAllByArtList(ArtList artList);
}
