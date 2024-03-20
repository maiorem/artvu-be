package com.art.api.user.infrastructure.repository;

import com.art.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<User, Long>, MemberRepositoryCustom {
}
