package com.rodem.shop.member.infrastructure;

import com.rodem.shop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {
}
