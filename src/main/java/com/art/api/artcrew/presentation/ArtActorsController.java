package com.art.api.artcrew.presentation;

import com.art.api.artcrew.application.ArtActorsService;
import com.art.api.core.response.ApiResponse;
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
    public ApiResponse<?> retrieveSameNameActorList(@PathVariable String name) {
        return ApiResponse.success("data", actorService.retrieveSameNameActorList(name));
    }

    @GetMapping("/actors/detail/{actorId}")
    public ApiResponse<?> retrieveActor(@PathVariable int actorId) {
        return ApiResponse.success("data", actorService.retrieveActor(actorId));
    }

    @GetMapping("/people/{artId}")
    public ApiResponse<?> retrievePeople(@PathVariable String artId) {
        return ApiResponse.success("data", actorService.retrievePeople(artId));
    }


}
