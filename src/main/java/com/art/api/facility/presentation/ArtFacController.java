package com.art.api.facility.presentation;

import com.art.api.core.response.ApiResponse;
import com.art.api.facility.application.ArtFacService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performs")
public class ArtFacController {

    private final ArtFacService artFacService;

    @GetMapping("/theater/{fac_id}")
    @Operation(summary = "극장 정보 (주소, 좌표 등)")
    public ApiResponse<?> retrieveFacDetail(@PathVariable(name = "fac_id") String facId) {
        return ApiResponse.success("data", artFacService.retrieveFacility(facId));
    }

    @GetMapping("/seat/{fac_id}")
    @Operation(summary = "극장별 좌석 정보")
    public ApiResponse<?> retrieveSeat(@PathVariable(name = "fac_id") String facId) {
        return ApiResponse.success("data", artFacService.regreiveTip(facId));
    }

}
