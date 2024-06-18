package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer>, ThemeRepositoryCustom {
    Optional<Theme> findByThemeNm(String themeNm);
}
