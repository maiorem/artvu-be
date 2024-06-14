package com.art.api.user.infrastructure.repository;

import com.art.api.product.domain.entity.ArtList;
import com.art.api.user.domain.entity.SaveHist;
import com.art.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveHistRepository extends JpaRepository<SaveHist, Integer> {
    Optional<SaveHist> findByArtListAndUser(ArtList artList, User user);

    void deleteByArtListAndUser(ArtList artList, User user);

    Long countByUser(User user);

    List<SaveHist> findByUser(User user);

    void deleteByUser(User user);
}
