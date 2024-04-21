package com.art.api.advertise.presentation;

import com.art.api.advertise.application.AdvertiseService;
import com.art.api.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performs")
public class AdvertiseController {

    private final AdvertiseService advertiseService;

    @GetMapping("/carousel")
    @Operation(summary = "광고 캐러셀 (입력해야 출력 가능)")
    public ApiResponse<?> retrieveCarousel(){
        return ApiResponse.success("data", advertiseService.retrieveCarousel());
    }
}
