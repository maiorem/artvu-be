package com.art.api.common.application;

import com.art.api.common.domain.entity.ArtArea;
import com.art.api.common.infrastructure.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    public List<ArtArea> areaList() {
        return localRepository.findAll();
    }

}
