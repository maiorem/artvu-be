package com.art.api.discover.infrastructure;

import com.art.api.discover.domain.dto.DiscoveryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscoverRepositoryCustom {

    Page<DiscoveryDTO> retrieveDiscover(Pageable pageable);
}
