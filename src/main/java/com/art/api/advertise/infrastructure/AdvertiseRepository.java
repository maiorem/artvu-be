package com.art.api.advertise.infrastructure;

import com.art.api.advertise.domain.entity.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdvertiseRepository extends JpaRepository<Carousel, Integer> {

}
