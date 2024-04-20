package com.art.api.user.infrastructure.repository;

import com.art.api.user.domain.entity.User;
import com.art.api.user.domain.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

    UserAuth findByUser(User user);

    void deleteByUser(User user);
}
