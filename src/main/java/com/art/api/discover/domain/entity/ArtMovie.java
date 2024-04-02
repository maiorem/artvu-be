package com.art.api.discover.domain.entity;

import com.art.api.core.entity.BaseEntity;
import com.art.api.product.domain.entity.ArtList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("영상")
@Table(name = "TB_ART_MOVIE")
public class ArtMovie  extends BaseEntity {

    @Id
    @Column(name = "ART_MV_ID")
    @Comment("영상번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artMvId;


    @Column(length = 200, name = "MV_URL")
    @Comment("영상URL")
    private String mvUrl;

    @JoinColumn(name = "ART_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArtList artlist;

    @Column(length = 1000, name = "DISCOVER_TITL")
    @Comment("디스커버 제목")
    private String discoverTitl;

    @Column(length = 4000, name = "DISCOVER_CONT")
    @Comment("디스커버 내용")
    private String discoverCont;

}
