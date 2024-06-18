package com.art.api.product.application;


import com.art.api.common.infrastructure.GenreRepository;
import com.art.api.common.infrastructure.LocalRepository;
import com.art.api.core.exception.ItemNotFoundException;
import com.art.api.discover.domain.entity.ArtMovie;
import com.art.api.discover.infrastructure.DiscoverRepository;
import com.art.api.facility.infrastructure.ArtFacRepository;
import com.art.api.product.domain.dto.ArtDetailDTO;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.dto.ThemeDTO;
import com.art.api.product.domain.dto.ThemeListDTO;
import com.art.api.product.domain.entity.*;
import com.art.api.product.infrastructure.*;
import com.art.api.user.domain.entity.SaveHist;
import com.art.api.user.infrastructure.repository.MemberRepository;
import com.art.api.user.infrastructure.repository.SaveHistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtService {

    private final ArtListRepository artListRepository;
    private final ArtDetailRepository detailRepository;
    private final LocalRepository areaRepository;
    private final GenreRepository genreRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final ArtImgRepository imgListRepository;
    private final ArtTimeRepository timeRepository;
    private final ArtFacRepository facilityRepository;
    private final ThemeHistRepository themeHistRepository;
    private final ThemeRepository themeRepository;
    private final DiscoverRepository discoverRepository;
    private final MemberRepository memberRepository;
    private final SaveHistRepository saveHistRepository;


    public Page<ArtListDTO> retrieveArtList(Pageable pageable, List<String> genre, String local, String search, User user) {
        Page<ArtListDTO> artList = convertArtList(artListRepository.findSearchResult(pageable, genre, local, search), user);
        return artList;
    }

    public Page<ArtListDTO> convertArtList(Page<ArtList> artList, User user) {
        List<ArtListDTO> list = new ArrayList<>();
        for (ArtList item : artList) {
            ArtListDTO dto = ArtListDTO.convertEntityToDto(item);
            Optional<List<ArtImg>> artImgList = imgListRepository.findAllByArtList(item);
            String posterUrl = "";
            if(artImgList.isPresent()) {
                posterUrl = artImgList.get().stream().filter(n -> n.getClsCode().equals(ClsCode.KOPIS)).findAny().orElse(ArtImg.builder().imgUrl("").build()).getImgUrl();
            }
            Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(item);
            if (item.getAreaCode() != null) {
                dto.setArea(item.getAreaCode().getAreaNm());
            }
            dto.setPosterImgUrl(posterUrl);
            mappingList.ifPresent(artGenreMppgs -> {
                for (ArtGenreMppg genre : artGenreMppgs) {
                    dto.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()));
                }
            });
            if (user != null) {
                Optional<com.art.api.user.domain.entity.User> optionalUser = memberRepository.findByUserId(user.getUsername());
                Optional<SaveHist> saved = saveHistRepository.findByArtListAndUser(item, optionalUser.get());
                if (saved.isPresent()) {
                    dto.setSaved(true);
                } else {
                    dto.setSaved(false);
                }
            }else {
                dto.setSaved(false);
            }
            list.add(dto);
        }

        Page<ArtListDTO> result = new PageImpl<>(list, artList.getPageable(), artList.getTotalElements());
        return result;
    }

    public List<ArtListDTO> convertArtList(List<ArtList> artList, com.art.api.user.domain.entity.User user) {
        List<ArtListDTO> list = new ArrayList<>();
        artList.forEach(item -> {

            ArtListDTO dto = ArtListDTO.convertEntityToDto(item);
            Optional<List<ArtImg>> artImgList = imgListRepository.findAllByArtList(item);
            String posterUrl = "";
            if(artImgList.isPresent()) {
                posterUrl = artImgList.get().stream().filter(n -> n.getClsCode().equals(ClsCode.KOPIS)).findAny().orElse(ArtImg.builder().imgUrl("").build()).getImgUrl();
            }
            Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(item);
            if (item.getAreaCode() != null) {
                dto.setArea(item.getAreaCode().getAreaNm());
            }
            dto.setPosterImgUrl(posterUrl);
            mappingList.ifPresent(artGenreMppgs -> {
                for (ArtGenreMppg genre : artGenreMppgs) {
                    dto.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()));
                }
            });
            if (user != null) {
                Optional<SaveHist> saved = saveHistRepository.findByArtListAndUser(item, user);
                if (saved.isPresent()) {
                    dto.setSaved(true);
                } else {
                    dto.setSaved(false);
                }
            }else {
                dto.setSaved(false);
            }
            list.add(dto);
        });
        return list;
    }



    public ArtDetailDTO retrieveArtDetail(String artId, User user) {
        Optional<ArtList> art = artListRepository.findByArtId(artId);
        if(art.isEmpty()) {
            return null;
        }
        Optional<ArtDetail> detail = detailRepository.findByArtId(artId);
        if (detail.isEmpty()) {
            return null;
        }
        Optional<ArtTime> optionalArtTime = timeRepository.findByArtlist(art.get());
        ArtTime time = null;
        if (optionalArtTime.isPresent()) {
            time = optionalArtTime.get();
        }
        ArtDetailDTO dto = ArtDetailDTO.convertEntityToDto(art.get(), detail.get(), time);

        Optional<List<ArtMovie>> artMovieListOptinal = discoverRepository.findAllByArtlist(art.get());
        artMovieListOptinal.ifPresent(artMovieList -> {
            for (ArtMovie artMovie : artMovieList) {
                if (artMovie.getMvUrl().contains("trailer")) {
                    dto.setTrailerUrl(artMovie.getMvUrl());
                }
            }
        });

        Optional<List<ArtImg>> artImgList = imgListRepository.findAllByArtList(art.get());
        artImgList.ifPresent(dto::setArtImgList);
        Optional<List<ArtGenreMppg>> mappingList = mappRepository.findAllByArtList(art.get());
        mappingList.ifPresent(artGenreMppgs -> {
            for (ArtGenreMppg genre : artGenreMppgs) {
                dto.getGenreList().add(genreRepository.findByArtGenreId(genre.getGenreList().getArtGenreId()));
            }
        });
        if (user != null) {
            Optional<com.art.api.user.domain.entity.User> optionalUser = memberRepository.findByUserId(user.getUsername());
            Optional<SaveHist> saved = saveHistRepository.findByArtListAndUser(art.get(), optionalUser.get());
            if (saved.isPresent()) {
                dto.setSaved(true);
            } else {
                dto.setSaved(false);
            }
        }else {
            dto.setSaved(false);
        }
        return dto;
    }

    // 메인 테마 list
    public List<ThemeListDTO> retrieveThemeList(User user){
        List<Theme> themeList = themeRepository.findAll();
        List<ThemeListDTO> dtoList = new ArrayList<>();
        Collections.sort(themeList);
        for (Theme theme : themeList) {
            List<ThemeDTO> list = new ArrayList<>();
            Optional<List<ThemeHist>> themeHistList = themeHistRepository.findByTheme(theme);
            if (themeHistList.isPresent()) {

                for (ThemeHist themeHist : themeHistList.get()) {
                    Optional<ArtList> art = artListRepository.findByArtId(themeHist.getArtList().getArtId());
                    if (art.isEmpty()) {
                        throw new ItemNotFoundException();
                    }
                    ThemeDTO dto = ThemeDTO.convertEntityToDto(art.get(), theme);
                    Optional<List<ArtImg>> artImgList = imgListRepository.findAllByArtList(art.get());
                    String posterUrl = "";
                    if(artImgList.isPresent()) {
                        posterUrl = artImgList.get().stream().filter(n -> n.getClsCode().equals(ClsCode.KOPIS)).findAny().orElse(ArtImg.builder().imgUrl("").build()).getImgUrl();
                    }
                    dto.setPosterImgUrl(posterUrl);
                    list.add(dto);
                }
            } else {
                return null;
            }
            ThemeListDTO newListDto = ThemeListDTO.convertEntityToDto(theme);
            newListDto.setContents(list);
            dtoList.add(newListDto);
        }

        return dtoList;

    }

    // 테마 이름별 API
    public List<ThemeDTO> retrieveThemeByName(String themeNm, User user) {
        List<ThemeDTO> list = new ArrayList<>();
        Optional<Theme> theme = themeRepository.findByThemeNm(themeNm);
        if(theme.isEmpty()) {
            return null;
        }
        Optional<List<ThemeHist>> themeHistList = themeHistRepository.findByTheme(theme.get());
        themeHistList.ifPresent(themeHists -> {
            for (ThemeHist themeHist : themeHists) {
                Optional<ArtList> art = artListRepository.findByArtId(themeHist.getArtList().getArtId());
                if (art.isEmpty()) {
                    throw new ItemNotFoundException();
                }
                ThemeDTO dto = ThemeDTO.convertEntityToDto(art.get(), theme.get());
                Optional<List<ArtImg>> artImgList = imgListRepository.findAllByArtList(art.get());
                String posterUrl = "";
                if(artImgList.isPresent()) {
                    posterUrl = artImgList.get().stream().filter(n -> n.getClsCode().equals(ClsCode.KOPIS)).findAny().orElse(ArtImg.builder().imgUrl("").build()).getImgUrl();
                }
                dto.setPosterImgUrl(posterUrl);
                list.add(dto);
            }
        });

        return list;
    }

    public Optional<ArtList> findByArtId(String artId) {
        return artListRepository.findByArtId(artId);
    }

}
