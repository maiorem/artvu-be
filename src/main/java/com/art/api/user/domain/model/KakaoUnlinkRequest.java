package com.art.api.user.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KakaoUnlinkRequest {
    private String target_id_type;
    private Long tartget_id;
}
