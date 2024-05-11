package com.art.api.discover.domain.entity;

import com.art.api.product.domain.entity.ArtList;
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
@Comment("영상내역")
@Table(name = "TB_ART_MOVIE_HIST")
public class ArtMovieHist {

    @Id
    @Column(name = "ART_HIST_NO")
    @Comment("영상내역번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artHistNo;

    @JoinColumn(name = "ART_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArtList artlist;

    @JoinColumn(name = "ART_MV_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArtMovie artMovie;

}
