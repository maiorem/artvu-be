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
@Comment("인증정보-중복가입방지(CIDI)")
@Table(name = "TB_CIDI")
public class Cidi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CIDI_NO")
    @Comment("CIDI번호")
    private int cidiNo;


    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 45, name = "CI")
    @Comment("CI값")
    private String ci;

    @Column(length = 45, name = "DI")
    @Comment("DI값")
    private String di;
}
