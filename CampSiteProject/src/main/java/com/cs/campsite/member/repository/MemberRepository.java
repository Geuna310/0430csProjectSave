package com.cs.campsite.member.repository;

import com.cs.campsite.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByMemberNickname(String memberNickname);
    boolean existsByMemberEmail(String email);
}
