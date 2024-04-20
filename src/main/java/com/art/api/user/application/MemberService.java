package com.art.api.user.application;

import com.art.api.user.domain.entity.*;
import com.art.api.user.domain.model.UpdateUserInfoRequest;
import com.art.api.user.infrastructure.repository.AuthSocialRepository;
import com.art.api.user.infrastructure.repository.MemberProfileRepository;
import com.art.api.user.infrastructure.repository.MemberRepository;
import com.art.api.user.infrastructure.repository.UserAuthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserAuthRepository userAuthRepository;
    private final AuthSocialRepository authSocialRepository;
    private final MemberProfileRepository profileRepository;

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
}
