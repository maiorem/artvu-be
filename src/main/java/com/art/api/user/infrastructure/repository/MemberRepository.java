package com.art.api.user.infrastructure.repository;

import com.art.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String id);

    void deleteByUserId(String userId);
}
