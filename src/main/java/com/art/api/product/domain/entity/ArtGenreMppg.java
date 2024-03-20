package com.art.api.product.domain.entity;


import com.art.api.common.domain.entity.GenreList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("상품장르연결내역")
@Table(name = "ART_GENRE_MPPG")
public class ArtGenreMppg {

    @Id
    @ManyToOne
    @JoinColumn(name = "ART_ID")
    @Comment("상품_상품ID")
    private ArtList artList;

    @Id
    @ManyToOne
    @JoinColumn(name = "ART_GENRE_ID")
    @Comment("장르_장르번호")
    private GenreList genreList;

}
