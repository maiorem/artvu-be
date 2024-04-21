package com.art.api.user.application;

import com.art.api.common.domain.entity.ArtArea;
import com.art.api.common.domain.entity.GenreList;
import com.art.api.core.exception.ItemNotFoundException;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.entity.ArtGenreMppg;
import com.art.api.product.domain.entity.ArtImg;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.domain.entity.ClsCode;
import com.art.api.product.infrastructure.ArtListRepository;
import com.art.api.user.domain.entity.*;
import com.art.api.user.domain.model.UpdateUserInfoRequest;
import com.art.api.user.infrastructure.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ArtListRepository artListRepository;
    private final MemberRepository memberRepository;
    private final UserAuthRepository userAuthRepository;
    private final AuthSocialRepository authSocialRepository;
    private final MemberProfileRepository profileRepository;
    private final SaveHistRepository saveHistRepository;

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

    @Transactional
    public void deleteUser(User user) {

        userAuthRepository.deleteByUser(user);
        authSocialRepository.deleteByUser(user);
        profileRepository.deleteByUser(user);
        memberRepository.deleteByUserId(user.getUserId());
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
        Long count = countSaveHistByUser(user);
        List<SaveHist> saveHistList = saveHistRepository.findByUser(user);
        if(saveHistList == null) {
            throw new ItemNotFoundException();
        }
        List<ArtList> list = new ArrayList<>();
        for (SaveHist saveHist : saveHistList) {
            Optional<ArtList> artOptional = artListRepository.findByArtId(saveHist.getArtList().getArtId());
            list.add(artOptional.get());
        }
        Page<ArtList> artListPage = new PageImpl<>(list, pagable, count);
        return artListPage;
    }

    // 선호 장르 목록 (map : { 장르명 : 갯수 }
    public Map<GenreList, Integer> retrievePreferGenreCount(User user) {
        List<SaveHist> saveHistList = saveHistRepository.findByUser(user);
        Map<GenreList, Integer> genreCount = new HashMap<>();
        for (SaveHist saveHist : saveHistList) {
            Optional<ArtList> artOptional = artListRepository.findByArtId(saveHist.getArtList().getArtId());
            List<ArtGenreMppg> genreMppgs = artOptional.get().getArtGenreMppgs();
            for (ArtGenreMppg genreMppg : genreMppgs) {
                genreCount.put(genreMppg.getGenreList(), genreCount.getOrDefault(genreMppg.getGenreList(), 0)+1);
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
        List<ArtList> suggestList = artListRepository.findSuggestList(genreList);
        return suggestList;
    }
}
