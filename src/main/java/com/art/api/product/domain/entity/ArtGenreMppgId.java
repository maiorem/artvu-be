package com.art.api.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtGenreMppgId implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "ART_ID")
    private String artList;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "ART_GENRE_ID")
    private String genreList;
}
