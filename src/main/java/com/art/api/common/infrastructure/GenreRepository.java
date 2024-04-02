package com.art.api.common.infrastructure;

import com.art.api.common.domain.entity.GenreList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreList, Integer> {
    GenreList findByArtGenreId(int artGenreId);
}
