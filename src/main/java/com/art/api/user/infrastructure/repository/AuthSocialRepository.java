package com.art.api.user.infrastructure.repository;

import com.art.api.user.domain.entity.AuthSocial;
import com.art.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthSocialRepository extends JpaRepository<AuthSocial, Integer> {
    AuthSocial findByUser(User user);

    AuthSocial findByUserAndRefreshToken(User user, String refreshToken);
}
