package com.art.api.discover.presentation;

import com.art.api.core.response.ApiResponse;
import com.art.api.discover.application.DiscoverService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performs")
@RequiredArgsConstructor
public class DiscoverController {

    private final DiscoverService discoverService;

    @GetMapping("/discovery")
    @Operation(summary = "디스커버")
    public ApiResponse<?> discovery(@PageableDefault(size = 1, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pagable) {
        return ApiResponse.success("data", discoverService.retrieveDiscovery(pagable));
    }


}
