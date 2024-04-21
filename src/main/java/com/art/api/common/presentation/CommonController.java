package com.art.api.common.presentation;


import com.art.api.common.application.GenreService;
import com.art.api.common.application.LocalService;
import com.art.api.common.domain.entity.ArtArea;
import com.art.api.common.domain.entity.GenreList;
import com.art.api.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performs")
public class CommonController {

    private final GenreService genreService;

    private final LocalService localService;

    @GetMapping("/genre")
    @Operation(summary = "장르 리스트")
    public ApiResponse<?> genreList() {
        List<GenreList> genreList = genreService.genreList();
        return ApiResponse.success("data", genreList);
    }

    @GetMapping("/local")
    @Operation(summary = "지역 리스트")
    public ApiResponse<?> areaList() {
        List<ArtArea> areaList = localService.areaList();
        return ApiResponse.success("data", areaList);
    }

}
