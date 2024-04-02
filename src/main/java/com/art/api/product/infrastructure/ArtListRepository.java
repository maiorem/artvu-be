package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtListRepository extends JpaRepository<ArtList, String>, ArtListRepositoryCustum {
}
