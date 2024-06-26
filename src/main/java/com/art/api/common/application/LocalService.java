package com.art.api.common.application;

import com.art.api.common.domain.entity.ArtArea;
import com.art.api.common.infrastructure.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    public List<ArtArea> areaList() {
        List<ArtArea> list = localRepository.findAll();
        Collections.sort(list);
        return list;
    }

}
