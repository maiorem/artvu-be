package com.art.api.user.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("인증정보-소셜로그인")
@Table(name = "TB_AUTH_SOCIAL")
@EntityListeners(AuditingEntityListener.class)
public class AuthSocial {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOCIAL_NO")
    @Comment("소셜로그인번호")
    private int socialNo;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @Column(length = 1, name = "SOCIAL_JOIN_TYPE")
    @Comment("소셜가입구분(1:apple, 2:goolge, 3: kakao, 4:naver)")
    private String socialJoinType;

    @Column(length = 64, name = "EXTERNAL_ID")
    @Comment("외부아이디(externale_id와 access_token값을 기록)")
    private String externalId;

    @Column(length = 256, name = "ACCESS_TOKEN")
    @Comment("접근토큰")
    private String accessToken;


    @Column(name = "MOD_DT")
    @Comment("수정일시")
    @LastModifiedDate
    private LocalDateTime modDt;
}
