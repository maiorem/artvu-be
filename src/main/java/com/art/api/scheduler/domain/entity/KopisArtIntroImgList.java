package com.art.api.scheduler.domain.entity;

import com.art.api.scheduler.domain.BaseRegDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "TB_KOPIS_ART_INTRO_IMG_LIST")
@IdClass(ArtInroImgListId.class)
@Comment("[KOPIS] 공연소개이미지목록")
public class KopisArtIntroImgList extends BaseRegDate {

    @Id
    @Column(length = 20, name = "ART_ID")
    @Comment("공연ID")
    private String artId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns( value = {
            @JoinColumn(name = "ART_ID", updatable = false, insertable = false),
            @JoinColumn(name = "ART_FAC_ID", updatable = false, insertable = false),
            @JoinColumn(name = "REG_DT", updatable = false, insertable = false)
    }, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private KopisArtDetail kopisArtDetail;

    @Column(length = 2000, name = "INTRODUCT_IMG_URL")
    @Comment("소개이미지URL")
    private String introductImgUrl;

}
