package com.art.api.scheduler.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class ArtInroImgListId implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "ART_ID")
    private String artId;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "REG_DT")
    private LocalDateTime regDt;
}
