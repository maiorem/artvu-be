package com.art.api.artcrew.presentation;

import com.art.api.artcrew.application.ArtActorsService;
import com.art.api.artcrew.domain.dto.PeopleDTO;
import com.art.api.artcrew.domain.entity.ArtActors;
import com.art.api.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performs")
public class ArtActorsController {

    private final ArtActorsService actorService;

    @GetMapping("/actors/{name}")
    @Operation(summary = "동명이인 배우 목록")
    public ApiResponse<?> retrieveSameNameActorList(@PathVariable(name = "name") String name) {
        List<ArtActors> actorsList = actorService.retrieveSameNameActorList(name);
        if(actorsList == null) {
            return ApiResponse.notExistData();
        }
        return ApiResponse.success("data", actorsList);
    }

    @GetMapping("/actors/detail/{actor_id}")
    @Operation(summary = "배우 단건")
    public ApiResponse<?> retrieveActor(@PathVariable(name = "actor_id") int actorId) {
        ArtActors artActors = actorService.retrieveActor(actorId);
        if (artActors == null) {
            return ApiResponse.notExistData();
        }
        return ApiResponse.success("data", artActors);
    }

    @GetMapping("/people/{art_id}")
    @Operation(summary = "공연 별 출연진 목록")
    public ApiResponse<?> retrievePeople(@PathVariable(name = "art_id") String artId) {
        PeopleDTO peopleDTO = actorService.retrievePeople(artId);
        if (peopleDTO == null) {
            return ApiResponse.notExistData();
        }
        return ApiResponse.success("data", peopleDTO);
    }


}
