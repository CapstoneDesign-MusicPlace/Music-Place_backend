package org.musicplace.member.repository;

import org.musicplace.member.domain.SignInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SignInRepository extends JpaRepository<SignInEntity,String> {
    SignInEntity findByMemberId(String memberId);
}
