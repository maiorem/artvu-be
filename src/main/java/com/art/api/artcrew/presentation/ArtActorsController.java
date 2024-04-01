package com.art.api.artcrew.presentation;

import com.art.api.artcrew.application.ArtActorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ArtActorsController {

    private final ArtActorsService actorService;



}
