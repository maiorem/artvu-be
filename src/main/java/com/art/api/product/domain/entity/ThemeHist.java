package com.art.api.product.domain.entity;

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
@Comment("테마내역")
@Table(name = "TB_THEME_HIST")
public class ThemeHist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_HIST_ID")
    private int ThemeHistId;

    @ManyToOne
    @JoinColumn(name = "THEME_ID")
    @Comment("테마아이디")
    private Theme theme;

    @ManyToOne
    @JoinColumn(name = "ART_ID")
    @Comment("상품아이디")
    private ArtList artList;
}
