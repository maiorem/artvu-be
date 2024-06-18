package com.art.api.product.infrastructure;

import com.art.api.product.domain.dto.ThemeDTO;
import com.art.api.product.domain.dto.ThemeListDTO;

import java.util.List;

public interface ThemeRepositoryCustom {

    List<ThemeDTO> findMainTheme();
}
