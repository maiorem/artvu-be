package com.art.api.product.domain.entity;

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

}
