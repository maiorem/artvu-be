package com.art.api.advertise.application;


import com.art.api.advertise.domain.entity.Carousel;
import com.art.api.advertise.infrastructure.AdvertiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertiseService {

    private final AdvertiseRepository advertiseRepository;

    public List<Carousel> retrieveCarousel(){
        List<Carousel> list = advertiseRepository.findAll();
        if ( list == null ) {
            return null;
        }
        return list;
    }


}
