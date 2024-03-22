package com.art.api.user.domain.entity;

import com.art.api.core.entity.BaseEntity;
import com.art.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("회원인증정보")
@Table(name = "TB_USER_AUTH")
@EntityListeners(AuditingEntityListener.class)
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTO_NO")
    @Comment("인증번호")
    private int authNo;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @Column(length = 1, name = "PRIVACY_AGRE_YN")
    @Comment("개인정보동의여부")
    private String privacyAgreYn;

    @Column(length = 45, name = "TELL_NO")
    @Comment("전화번호")
    private String tellNo;

    @Column(length = 150, name = "EMAIL")
    @Comment("이메일주소")
    private String email;

    @Column(length = 8, name = "BIRTH_DT")
    @Comment("생년월일(예시:20240314)")
    private String birthdayDt;


    @Column(length = 1, name = "SEX")
    @Comment("성별(M,F)")
    private String sex;

    @Column(length = 100, name = "JOB_NM")
    @Comment("직업")
    private String jobNm;

    @Column(length = 20, name = "NATIONAL_TYPE")
    @Comment("내외국인구분(내국인:NATIVE, 외국인:NON-NATIVE)")
    private String nationalType;


    @Column(name = "AUTH_DT")
    @Comment("본인인증날짜")
    @CreatedDate
    private LocalDateTime authDt;

}
