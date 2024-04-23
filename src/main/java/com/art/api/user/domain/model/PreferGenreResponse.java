package com.art.api.user.domain.model;

import com.art.api.common.domain.entity.GenreList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PreferGenreResponse {

    Long totCount;

    List<PreferList> content;

    @Getter @Setter
    public static class PreferList {
        int selectCount;
        GenreList genreList;
    }
}
