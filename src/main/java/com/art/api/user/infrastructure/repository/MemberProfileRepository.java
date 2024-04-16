package com.art.api.user.infrastructure.repository;

import com.art.api.user.domain.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Integer> {
}
