package org.musicplace.member.repository;

import org.musicplace.member.domain.SignInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignInRepository extends JpaRepository<SignInEntity,String> {
}
