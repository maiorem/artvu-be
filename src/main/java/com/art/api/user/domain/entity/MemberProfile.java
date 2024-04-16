package com.art.api.user.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("회원프로필")
@Table(name = "TB_MEMBER_PROFILE")
@EntityListeners(AuditingEntityListener.class)
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_NO")
    @Comment("프로필번호")
    private int profileNo;


    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @Column(length = 100, name = "NICK_NM")
    @Comment("닉네임")
    private String nickNm;


    @Column(name = "JOIN_DT")
    @Comment("가입일시")
    @CreatedDate
    private LocalDateTime joinDt;


    @Column(name = "MOD_DT")
    @Comment("수정일시")
    @LastModifiedDate
    private LocalDateTime modDt;

}
