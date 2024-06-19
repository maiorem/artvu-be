package com.art.api.product.application;


import com.art.api.common.infrastructure.GenreRepository;
import com.art.api.core.exception.ItemNotFoundException;
import com.art.api.discover.domain.entity.ArtMovie;
import com.art.api.discover.infrastructure.DiscoverRepository;
import com.art.api.product.domain.dto.*;
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

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtService {

    private final ArtListRepository artListRepository;
    private final ArtDetailRepository detailRepository;
    private final GenreRepository genreRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final ArtImgRepository imgListRepository;
    private final ArtTimeRepository timeRepository;
    private final ThemeHistRepository themeHistRepository;
    private final ThemeRepository themeRepository;
    private final DiscoverRepository discoverRepository;
    private final MemberRepository memberRepository;
    private final SaveHistRepository saveHistRepository;


    public Page<ArtListDTO> retrieveArtList(Pageable pageable, List<String> genre, String local, String search, User user) {
        return convertArtList(artListRepository.findSearchResult(pageable, genre, local, search), user);
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

            dto.setSaved(loginUserIsSaved(user, item));
            list.add(dto);
        }

        return new PageImpl<>(list, artList.getPageable(), artList.getTotalElements());
    }

    public List<ArtListDTO> convertArtList(List<ArtList> artList, User user) {
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
            dto.setSaved(loginUserIsSaved(user, item));
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
        dto.setSaved(loginUserIsSaved(user, art.get()));
        return dto;
    }


    // 메인 테마 list
    public List<ThemeListDTO> retrieveThemeList(User user){
        List<ThemeListDTO> dtoList = new ArrayList<>();
        List<ThemeDTO> list = themeRepository.findMainTheme();

        if (list != null) {
            for (ThemeDTO themeDTO : list) {
                Optional<ArtList> byArtId = artListRepository.findByArtId(themeDTO.getArtId());
                if (byArtId.isPresent()) {
                    themeDTO.setSaved(loginUserIsSaved(user, byArtId.get()));
                }
            }
            List<List<ThemeDTO>> lists = new ArrayList<>(list.stream().collect(Collectors.groupingBy(themeDTO -> themeDTO.getThemeOrdNo())).values());
            for (List<ThemeDTO> themeDTOS : lists) {
                ThemeListDTO newListDto = new ThemeListDTO();
                newListDto.setThemeNm(themeDTOS.get(0).getThemeNm());
                newListDto.setThemeOrdNo(themeDTOS.get(0).getThemeOrdNo());
                newListDto.setContents(themeDTOS);
                dtoList.add(newListDto);
            }
        } else {
            return null;
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


    private boolean loginUserIsSaved(User user, ArtList artList) {
        boolean isSaved = false;
        if (user != null) {
            Optional<com.art.api.user.domain.entity.User> optionalUser = memberRepository.findByUserId(user.getUsername());
            Optional<SaveHist> saved = saveHistRepository.findByArtListAndUser(artList, optionalUser.get());
            if (saved.isPresent()) {
                isSaved = true;
            }
        }
        return isSaved;
    }

}
