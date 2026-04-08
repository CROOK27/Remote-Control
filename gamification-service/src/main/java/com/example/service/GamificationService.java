package com.example.service;

import com.example.event.AnswerSavedEvent;
import com.example.event.SessionCompletedEvent;
import com.example.dto.UserPointsDto;
import com.example.dto.PointsDto;
import com.example.entity.PointsHistory;
import com.example.entity.UserPoints;
import com.example.repository.PointsHistoryRepository;
import com.example.repository.UserPointsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamificationService {

    private final UserPointsRepository userPointsRepository;
    private final PointsHistoryRepository pointsHistoryRepository;

    @Value("${gamification.points-per-correct-answer:10}")
    private int pointsPerCorrectAnswer;

    @Value("${gamification.points-per-session-completion:50}")
    private int pointsPerSessionCompletion;

    @Transactional
    public void processCorrectAnswer(AnswerSavedEvent event) {
        if (event.getIsCorrect() == null || !event.getIsCorrect()) {
            log.debug("Skipping incorrect answer for session: {}", event.getSessionId());
            return;
        }

        Long userId = event.getUserId();
        if (userId == null) {
            log.warn("UserId is null in event: {}", event);
            return;
        }

        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPoints newUserPoints = new UserPoints();
                    newUserPoints.setUserId(userId);
                    return newUserPoints;
                });

        userPoints.setTotalPoints(userPoints.getTotalPoints() + pointsPerCorrectAnswer);
        userPointsRepository.save(userPoints);

        PointsHistory history = new PointsHistory();
        history.setUserId(userId);
        history.setSessionId(event.getSessionId());
        history.setQuestionId(event.getQuestionId());
        history.setPointsEarned(pointsPerCorrectAnswer);
        pointsHistoryRepository.save(history);

        log.info("Awarded {} points to user {} for correct answer on question {}",
                pointsPerCorrectAnswer, userId, event.getQuestionId());
    }

    @Transactional
    public void processSessionCompleted(SessionCompletedEvent event) {
        Long userId = event.getUserId();
        if (userId == null) {
            log.warn("UserId is null in event: {}", event);
            return;
        }

        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPoints newUserPoints = new UserPoints();
                    newUserPoints.setUserId(userId);
                    return newUserPoints;
                });

        userPoints.setTotalPoints(userPoints.getTotalPoints() + pointsPerSessionCompletion);
        userPointsRepository.save(userPoints);

        PointsHistory history = new PointsHistory();
        history.setUserId(userId);
        history.setSessionId(event.getSessionId());
        history.setQuestionId(null);
        history.setPointsEarned(pointsPerSessionCompletion);
        pointsHistoryRepository.save(history);

        log.info("Awarded {} points to user {} for completing session {} with score {}",
                pointsPerSessionCompletion, userId, event.getSessionId(), event.getScore());
    }

    public UserPointsDto getUserPoints(Long userId) {
        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPoints empty = new UserPoints();
                    empty.setUserId(userId);
                    empty.setTotalPoints(0);
                    return empty;
                });

        return new UserPointsDto(userPoints.getUserId(), userPoints.getTotalPoints());
    }

    public List<UserPointsDto> getTopUsers(int limit) {
        return userPointsRepository.findAll().stream()
                .sorted((a, b) -> b.getTotalPoints().compareTo(a.getTotalPoints()))
                .limit(limit)
                .map(up -> new UserPointsDto(up.getUserId(), up.getTotalPoints()))
                .collect(Collectors.toList());
    }

    public List<PointsDto> getUserHistory(Long userId) {
        return pointsHistoryRepository.findByUserId(userId).stream()
                .map(ph -> new PointsDto(ph.getSessionId(), ph.getQuestionId(),
                        ph.getPointsEarned(), ph.getEarnedAt()))
                .collect(Collectors.toList());
    }
}