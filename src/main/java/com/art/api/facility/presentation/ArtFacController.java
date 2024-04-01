package com.art.api.facility.presentation;

import com.art.api.core.response.ApiResponse;
import com.art.api.facility.application.ArtFacService;
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

    @GetMapping("/theater/{facId}")
    public ApiResponse<?> retrieveFacDetail(@PathVariable int facId) {
        return ApiResponse.success("data", artFacService.retrieveFacility(facId));
    }

}
