package com.art.api.product.domain.entity;

import com.art.api.common.domain.entity.GenreList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "TB_ART_LIST")
@Comment("상품목록")
@NoArgsConstructor
@AllArgsConstructor
public class ArtList {

    @Id
    @Column(length = 20)
    private String artId;

    @Column(length = 200, name = "ART_NM")
    @Comment("공연명")
    private String artNm;

    @Column(length = 20, name = "ART_FAC_ID")
    @Comment("공연시설ID")
    private String artFacId;

    @Column(length = 20, name = "AREA_CODE")
    @Comment("지역정보-지역코드")
    private String areaCode;

    @Column(name = "ORG_PRICE")
    @Comment("원본가격")
    private int orgPrice;


    @Column(name = "MIN_PRICE")
    @Comment("최저가격")
    private int minPrice;

    @Column(name = "MIN_PRICE_REG_DT")
    @Comment("최저가격기준시간")
    private LocalDateTime minPriceRegDt;

    @Column(length = 10, name = "STATUS")
    @Comment("공연상태")
    private String status;

    @Column(length = 2000, name = "COPY_TEXT")
    @Comment("카피라이터")
    private String copyText;

    @OneToMany(mappedBy = "artList")
    private List<ArtGenreMppg> artGenreMppgs = new ArrayList<>();
}
