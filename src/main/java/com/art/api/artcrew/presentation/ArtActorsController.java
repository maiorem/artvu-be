package com.art.api.artcrew.presentation;

import com.art.api.artcrew.application.ArtActorsService;
import com.art.api.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performs")
public class ArtActorsController {

    private final ArtActorsService actorService;

    @GetMapping("/actors/{name}")
    @Operation(summary = "동명이인 배우 목록")
    public ApiResponse<?> retrieveSameNameActorList(@PathVariable(name = "name") String name) {
        return ApiResponse.success("data", actorService.retrieveSameNameActorList(name));
    }

    @GetMapping("/actors/detail/{actor_id}")
    @Operation(summary = "배우 단건")
    public ApiResponse<?> retrieveActor(@PathVariable(name = "actor_id") int actorId) {
        return ApiResponse.success("data", actorService.retrieveActor(actorId));
    }

    @GetMapping("/people/{art_id}")
    @Operation(summary = "공연 별 출연진 목록")
    public ApiResponse<?> retrievePeople(@PathVariable(name = "art_id") String artId) {
        return ApiResponse.success("data", actorService.retrievePeople(artId));
    }


}
