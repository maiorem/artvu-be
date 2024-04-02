package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtListRepository extends JpaRepository<ArtList, String>, ArtListRepositoryCustum {

    Optional<ArtList> findByArtId(String artId);

}
