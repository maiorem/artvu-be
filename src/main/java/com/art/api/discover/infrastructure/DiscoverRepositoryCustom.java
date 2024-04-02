package com.art.api.discover.infrastructure;

import com.art.api.discover.domain.entity.ArtMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscoverRepositoryCustom {

    Page<ArtMovie> retrieveDiscover(Pageable pageable);
}
