package com.art.api.product.infrastructure;

import com.art.api.product.domain.entity.ArtList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtListRepositoryCustum {

    Page<ArtList> findSearchResult(Pageable pageable, String genre, String local, String search);

}
