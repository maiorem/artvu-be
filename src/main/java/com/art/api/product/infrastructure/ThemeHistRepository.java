package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.Theme;
import com.art.api.product.domain.entity.ThemeHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeHistRepository extends JpaRepository<ThemeHist, Integer> {
    Optional<List<ThemeHist>> findByTheme(Theme theme);
}
