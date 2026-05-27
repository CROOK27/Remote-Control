package com.example.service;

import com.example.dto.InvitationCodeResponse;
import com.example.entity.InvitationCode;
import com.example.repository.InvitationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationCodeRepository invitationCodeRepository;
    private final SecureRandom random = new SecureRandom();

    @Transactional
    public InvitationCodeResponse generateCode(Long teacherId) {
        String code = generate6DigitCode();
        InvitationCode invitation = new InvitationCode();
        invitation.setCode(code);
        invitation.setTeacherId(teacherId);
        invitation.setCreatedAt(LocalDateTime.now());
        invitation.setExpiresAt(LocalDateTime.now().plusDays(7)); // срок действия 7 дней
        invitation.setUsed(false);
        invitationCodeRepository.save(invitation);
        return new InvitationCodeResponse(code, teacherId, invitation.getExpiresAt());
    }

    @Transactional
    public void validateAndUseCode(String code, Long newUserId) {
        InvitationCode invitation = invitationCodeRepository.findByCodeAndUsedFalse(code)
                .orElseThrow(() -> new RuntimeException("Invalid or expired invitation code"));
        if (invitation.getExpiresAt() != null && invitation.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invitation code has expired");
        }
        invitation.setUsed(true);
        invitation.setUsedByUserId(newUserId);
        invitationCodeRepository.save(invitation);
    }

    private String generate6DigitCode() {
        int number = random.nextInt(900000) + 100000; // от 100000 до 999999
        return String.valueOf(number);
    }
}