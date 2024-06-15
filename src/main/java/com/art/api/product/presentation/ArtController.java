package com.art.api.product.presentation;


import com.art.api.core.response.ApiResponse;
import com.art.api.product.application.ArtService;
import com.art.api.product.domain.dto.ArtDetailDTO;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.dto.ThemeDTO;
import com.art.api.product.domain.dto.ThemeListDTO;
import com.art.api.user.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performs")
public class ArtController {

    private final ArtService artService;
    private final MemberService memberService;

    @GetMapping()
    @Operation(summary = "공연 목록 (검색, 지역, 장르)")
    public ApiResponse<?> artList(@RequestParam(required = false, name = "genre") @Schema(description = "장르명") List<String> genre,
                                  @RequestParam(required = false, name = "local") @Schema(description = "지역명") String local,
                                  @RequestParam(required = false, name = "search") @Schema(description = "검색키워드") String search,
                                  @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pagable,
                                  @AuthenticationPrincipal @Parameter(hidden = true) User user){

        Page<ArtListDTO> artListDTOS = artService.retrieveArtList(pagable, genre, local, search, user);
        if(artListDTOS == null) {
            return ApiResponse.notExistData();
        }

        return ApiResponse.success("data", artListDTOS);
    }

    @GetMapping("/{art_id}")
    @Operation(summary = "공연 상세")
    public ApiResponse<?> artDetail(@PathVariable(name = "art_id") String artId,
                                    @AuthenticationPrincipal @Parameter(hidden = true) User user){
        ArtDetailDTO artDetailDTO = artService.retrieveArtDetail(artId, user);
        if (artDetailDTO == null) {
            return ApiResponse.notExistData();
        }
        return ApiResponse.success("data", artDetailDTO);
    }

    @GetMapping("/theme")
    @Operation(summary = "메인 테마별 공연")
    public ApiResponse<?> retrieveThemeList(@AuthenticationPrincipal @Parameter(hidden = true) User user){
        List<ThemeListDTO> list = artService.retrieveThemeList(user);
        if (list == null) {
            return ApiResponse.notExistData();
        }
        return ApiResponse.success("data", list);
    }

    @GetMapping("/theme/{theme_name}")
    @Operation(summary = "테마명으로 공연 찾기")
    public ApiResponse<?> retrieveThemeList(@PathVariable(name = "theme_name") String themeNm,
                                            @AuthenticationPrincipal @Parameter(hidden = true) User user){
        List<ThemeDTO> list = artService.retrieveThemeByName(themeNm, user);
        if (list == null) {
            return ApiResponse.notExistData();
        }
        return ApiResponse.success("data", list);
    }


}
