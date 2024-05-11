package com.art.api.common.application;

import com.art.api.common.domain.entity.GenreList;
import com.art.api.common.infrastructure.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreList> genreList() {
        List<GenreList> list = genreRepository.findAll();
        Collections.sort(list);
        return list;
    }

}
