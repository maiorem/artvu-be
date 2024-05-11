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
@Comment("테마목록")
@Table(name = "TB_THEME")
public class Theme extends BaseEntity implements Comparable<Theme> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_ID")
    private int themeId;

    @Column(length = 50, name = "THEME_NM")
    @Comment("테마명")
    private String themeNm;

    @Column(name = "THEME_ORD_NO")
    @Comment("테마순서")
    private int themeOrdNo;

    @Override
    public int compareTo(Theme o) {
        return this.themeOrdNo - o.themeOrdNo;
    }
}
