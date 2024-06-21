package com.art.api.user.application;

import com.art.api.common.domain.entity.GenreList;
import com.art.api.core.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.art.api.core.exception.ClientUserNotFoundException;
import com.art.api.core.utils.CookieUtil;
import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.infrastructure.ArtGenreMppgRepository;
import com.art.api.product.infrastructure.ArtListRepository;
import com.art.api.user.domain.entity.*;
import com.art.api.user.domain.model.UpdateUserInfoRequest;
import com.art.api.user.infrastructure.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final ArtListRepository artListRepository;
    private final MemberRepository memberRepository;
    private final UserAuthRepository userAuthRepository;
    private final AuthSocialRepository authSocialRepository;
    private final MemberProfileRepository profileRepository;
    private final SaveHistRepository saveHistRepository;
    private final ArtGenreMppgRepository mappRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Value("${spring.open-api.kakaoAdminKey}")
    private String kakaoAdminKey;

    private final static String REFRESH_TOKEN = "refresh_token";

    /**
     * 로그인 유저정보 가져오기
     */
    public Optional<User> getClientUser(String username) {
        return memberRepository.findByUserId(username);
    }

    public AuthSocial getAuthInfo(User user) {
        return authSocialRepository.findByUser(user);
    }

    public UserAuth getUserInfo(User user) {
        return userAuthRepository.findByUser(user);
    }

    public MemberProfile getProfile(User user) {
        return profileRepository.findByUser(user);
    }



    public void logout(HttpServletRequest request, HttpServletResponse response, String userId) {
        Optional<User> byUserId = memberRepository.findByUserId(userId);
        if (byUserId.isEmpty()) {
            throw new ClientUserNotFoundException();
        }
        //카카오 로그아웃 (토큰 만료)
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(converters);

        //header 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "KakaoAK " + kakaoAdminKey);

        //parameter 세팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("target_id_type", "user_id");
        map.add("target_id", userId);

        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(map, headers);

        String json = restTemplate.postForObject("https://kapi.kakao.com/v1/user/logout", kakaoRequest, String.class);
        log.info("----------------- 응답 결과 -------------------");
        log.info(json);
        // 쿠키삭제
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    @Transactional
    public void deleteUser(HttpServletRequest request, HttpServletResponse response,User user) {

        //카카오 연결 해제
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(converters);

        //header 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "KakaoAK " + kakaoAdminKey);

        //parameter 세팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("target_id_type", "user_id");
        map.add("target_id", user.getUserId());

        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(map, headers);

        String json = restTemplate.postForObject("https://kapi.kakao.com/v1/user/unlink", kakaoRequest, String.class);
        log.info("----------------- 응답 결과 -------------------");
        log.info(json);

        saveHistRepository.deleteByUser(user);
        profileRepository.deleteByUser(user);
        authSocialRepository.deleteByUser(user);
        userAuthRepository.deleteByUser(user);
        memberRepository.deleteByUserId(user.getUserId());

        // 쿠키삭제
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

    }



    @Transactional
    public void updateUserInfo(User user, UpdateUserInfoRequest request) {
        UserAuth userInfo = getUserInfo(user);
        if (userInfo == null) {
            UserAuth updateUserInfo = UserAuth.builder()
                    .sex(Gender.valueOf(request.getGender()))
                    .birthdayDt(request.getBirthDate())
                    .jobNm(request.getJobNm())
                    .tellNo(request.getTellNo())
                    .nationalType(NationalType.valueOf(request.getNationalType()))
                    .build();
            userAuthRepository.saveAndFlush(updateUserInfo);
        } else {
            userInfo.setBirthdayDt(request.getBirthDate());
            userInfo.setSex(Gender.valueOf(request.getGender()));
            userInfo.setBirthdayDt(request.getBirthDate());
            userInfo.setJobNm(request.getJobNm());
            userInfo.setTellNo(request.getTellNo());
            userInfo.setNationalType(NationalType.valueOf(request.getNationalType()));
        }
        MemberProfile profile = getProfile(user);
        if (profile == null) {
            MemberProfile updateProfile = MemberProfile.builder()
                    .nickNm(request.getNickNm())
                    .build();
            profileRepository.saveAndFlush(updateProfile);
        } else {
            profile.setNickNm(request.getNickNm());
        }
    }


    // 저장(true) / 저장취소(false)
    @Transactional
    public boolean updateSaveHist(User user, ArtList artList) {
        Optional<SaveHist> histOptional = saveHistRepository.findByArtListAndUser(artList, user);
        if (histOptional.isEmpty()) {
            //저장
            SaveHist saveHist = SaveHist.builder()
                    .artList(artList)
                    .user(user)
                    .build();
            saveHistRepository.saveAndFlush(saveHist);
            return true;
        } else {
            saveHistRepository.deleteByArtListAndUser(artList, user);
            return false;
        }

    }

    // 유저가 가진 저장 연극 수 반환
    public Long countSaveHistByUser(User user) {
        return saveHistRepository.countByUser(user);
    }

    // 유저가 저장한 연극 목록 페이징
    public Page<ArtList> retrieveMySaveList(Pageable pagable, User user) {
        List<SaveHist> saveHistList = saveHistRepository.findByUser(user);
        if(saveHistList == null) {
            return null;
        }
        List<String> saveArtIdList = new ArrayList<>();
        for (SaveHist saveHist : saveHistList) {
            saveArtIdList.add(saveHist.getArtList().getArtId());
        }
        return artListRepository.findSaveResult(pagable, saveArtIdList);
    }

    // 선호 장르 목록 (map : { 장르명 : 갯수 }
    public Map<GenreList, Integer> retrievePreferGenreCount(User user) {
        List<SaveHist> saveHistList = saveHistRepository.findByUser(user);
        Map<GenreList, Integer> genreCount = new HashMap<>();
        for (SaveHist saveHist : saveHistList) {
            Optional<ArtList> artOptional = artListRepository.findByArtId(saveHist.getArtList().getArtId());
            Optional<List<ArtGenreMppg>> genreMppgs = mappRepository.findAllByArtList(artOptional.get());
            if (genreMppgs.isPresent()) {
                for (ArtGenreMppg genreMppg : genreMppgs.get()) {
                    genreCount.put(genreMppg.getGenreList(), genreCount.getOrDefault(genreMppg.getGenreList(), 0) + 1);
                }
            }

        }
        // 내림차순 정렬
        List<GenreList> keySet = new ArrayList<>(genreCount.keySet());
        keySet.sort((o1, o2) -> genreCount.get(o2).compareTo(genreCount.get(o1)));
        return genreCount;
    }

    // 추천 공연 목록
    public List<ArtList> retrieveListSuggestList(User user) {
        Map<GenreList, Integer> genreCount = retrievePreferGenreCount(user);
        List<GenreList> genreList = new ArrayList<>();
        for (GenreList genre : genreCount.keySet()) {
            if(genreCount.get(genre) > 0) {
                genreList.add(genre);
            }
        }
        return artListRepository.findSuggestList(genreList);
    }

    public User findByUserId(String userId) {
        Optional<User> byUserId = memberRepository.findByUserId(userId);
        if (byUserId.isEmpty()) {
            throw new ClientUserNotFoundException();
        }
        return byUserId.get();
    }

}
