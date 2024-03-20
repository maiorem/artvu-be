package com.art.api.common.domain.entity;

import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.domain.entity.ArtList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("장르목록")
@Table(name = "TB_ART_GENRE_LIST")
public class GenreList {

    @Id
    @Column(name = "ART_GENRE_ID")
    @Comment("장르번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artGenreId;


    @Column(length = 50, name = "ART_GENRE_NM")
    @Comment("장르명")
    private String artGenreNm;

    @OneToMany(mappedBy = "genreList")
    private List<ArtGenreMppg> artGenreMppgs = new ArrayList<>();


}
