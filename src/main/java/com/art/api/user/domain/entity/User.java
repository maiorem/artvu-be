package com.art.api.user.domain.entity;

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
@Comment("회원")
@Table(name = "TB_USER")
public class User {

    @Id
    @Column(length = 200, name = "USER_ID")
    @Comment("사용자 아이디")
    private String userId;

    @Column(length = 50, name = "USER_NAME")
    @Comment("사용자명")
    private String userName;

    @Column(name = "LOGIN_TYPE")
    @Comment("로그인타입(1:IDPW, 2:SOCIAL)")
    private int loginType;

}
