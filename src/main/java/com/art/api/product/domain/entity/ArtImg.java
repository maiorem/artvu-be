package com.art.api.product.domain.entity;


import com.art.api.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("연관이미지")
@Table(name = "TB_ART_IMG")
public class ArtImg  extends BaseEntity {
    @Id
    @Column(name = "ART_IMG_ID")
    @Comment("이미지ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artImgId;

    @Column(length = 50, name = "CLS_CODE")
    @Comment("용도구분(포스터,소개,좌석)")
    private String clsCode;

    @Column(length = 2000, name = "IMG_URL")
    @Comment("이미지URL")
    private String imgUrl;

    @JoinColumn(name = "ART_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArtList artList;
}
