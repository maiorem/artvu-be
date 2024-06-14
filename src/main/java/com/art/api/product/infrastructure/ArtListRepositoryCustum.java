package com.art.api.product.infrastructure;

import com.art.api.common.domain.entity.GenreList;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.user.domain.entity.SaveHist;
import com.art.api.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtListRepositoryCustum {

    Page<ArtList> findSearchResult(Pageable pageable, List<String> genre, String local, String search);

    Page<ArtList> findSaveResult(Pageable pageable, List<String> saveArtIdList);

    List<ArtList> findSuggestList(List<GenreList> genreList);
}
