package com.art.api.user.domain.entity;


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
@Comment("구성원")
@Table(name = "TB_FAMILY")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAMILY_NO")
    @Comment("구성원번호")
    private int familyNo;

    @Column(length = 20, name = "REL_NM")
    @Comment("관계명(부,모,자녀)")
    private String relNm;

    @Column(length = 20, name = "FULL_NM")
    @Comment("이름")
    private String fullNm;

    @Column(length = 1, name = "SEX")
    @Comment("성별(남자:M, 여자:F)")
    private String sex;

    @Column(name = "AGE")
    @Comment("나이")
    private int age;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
