package com.art.api.advertise.domain.entity;

import com.art.api.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("광고")
@Table(name = "TB_CAROUSEL")
public class Carousel extends BaseEntity {

    @Id
    @Column(name = "CAROUSEL_ID")
    @Comment("광고ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carouselId;

    @Column(length = 2000, name = "CAROUSEL_TXT")
    @Comment("광고문구")
    private String carouselText;

    @Column(length = 2000, name = "CAROUSEL_IMG_URL")
    @Comment("광고이미지경로")
    private String carouselImgUrl;

}
