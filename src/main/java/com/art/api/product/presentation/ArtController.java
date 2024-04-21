package com.art.api.product.presentation;


import com.art.api.core.response.ApiResponse;
import com.art.api.product.application.ArtService;
import com.art.api.user.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performs")
public class ArtController {

    private final ArtService artService;
    private final MemberService memberService;

    @GetMapping()
    @Operation(summary = "공연 목록 (검색, 지역, 장르)")
    public ApiResponse<?> artList(@RequestParam(required = false, name = "genre") @Schema(description = "장르명") String genre,
                                  @RequestParam(required = false, name = "local") @Schema(description = "지역명") String local,
                                  @RequestParam(required = false, name = "search") @Schema(description = "검색키워드") String search,
                                  @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pagable){


        return ApiResponse.success("data", artService.retrieveArtList(pagable, genre, local, search));
    }

    @GetMapping("/{art_id}")
    @Operation(summary = "공연 상세")
    public ApiResponse<?> artDetail(@PathVariable(name = "art_id") String artId){
        return ApiResponse.success("data", artService.retrieveArtDetail(artId));
    }

    @GetMapping("/theme/{theme_nm}")
    @Operation(summary = "테마이름 별 공연")
    public ApiResponse<?> retrieveThemeList(@PathVariable(name = "theme_nm") String themeNm){
        return ApiResponse.success("data", artService.retrieveThemeList(themeNm));
    }


}
