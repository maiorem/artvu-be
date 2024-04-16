package com.art.api.user.application;

import com.art.api.user.domain.entity.AuthSocial;
import com.art.api.user.domain.entity.User;
import com.art.api.user.domain.oauth.UserPrincipal;
import com.art.api.user.infrastructure.repository.AuthSocialRepository;
import com.art.api.user.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthSocialRepository authSocialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> clientUser = memberRepository.findByUserId(username);
        if(clientUser.isEmpty()) {

        }
        Optional<AuthSocial> auth = authSocialRepository.findByUser(clientUser.get());
        if(auth.isEmpty()) {

        }

        return UserPrincipal.create(auth.get(), clientUser.get().getUserId());
    }
}
