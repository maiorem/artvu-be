package com.art.api.product.domain.entity;

import com.art.api.core.entity.BaseEntity;
import com.art.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import reactor.netty.udp.UdpServer;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("저장하기내역")
@Table(name = "TB_SAVE_HIST ")
public class SaveHist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SAVE_HIST_ID")
    private int saveId;

    @ManyToOne
    @Comment("회원아이디")
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @Comment("상품아이디")
    @JoinColumn(name = "ART_ID")
    private ArtList artList;


}
