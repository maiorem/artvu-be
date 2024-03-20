package com.art.api.artcrew.domain.entity;


import com.art.api.product.domain.entity.ArtList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("출연진")
@Table(name = "TB_ART_ACTORS")
public class ArtActors {

    @Id
    @Column(name = "ART_ACTORS_ID")
    @Comment("출연자번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artActorsId;

    @Column(length = 50, name = "ACTORS_NM")
    @Comment("장르명")
    private String actorsNm;

    @Column(length = 50, name = "ACTORS_IMG_URL")
    @Comment("출연자이미지URL")
    private String actorsImgUrl;

    @JoinColumn(name = "ART_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArtList artId;
}
