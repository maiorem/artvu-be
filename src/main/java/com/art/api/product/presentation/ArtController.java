package com.art.api.product.presentation;


import com.art.api.core.response.ApiResponse;
import com.art.api.product.application.ArtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performs")
public class ArtController {

    private final ArtService artService;

    @GetMapping()
    @Operation(description = "공연 목록 (검색, 지역, 장르)")
    public ApiResponse<?> artList(@RequestParam(required = false) @Schema(description = "장르명") String genre,
                                  @RequestParam(required = false) @Schema(description = "지역명") String local,
                                  @RequestParam(required = false) @Schema(description = "검색키워드") String search,
                                  @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pagable){


        return ApiResponse.success("data", artService.retrieveArtList(pagable, genre, local, search));
    }

    @GetMapping("/{artId}")
    @Operation(description = "공연 상세")
    public ApiResponse<?> artDetail(@PathVariable String artId){
        return ApiResponse.success("data", artService.retrieveArtDetail(artId));
    }

}
