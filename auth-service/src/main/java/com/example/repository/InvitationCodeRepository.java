package com.example.repository;

import com.example.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {
    Optional<InvitationCode> findByCodeAndUsedFalse(String code);
}