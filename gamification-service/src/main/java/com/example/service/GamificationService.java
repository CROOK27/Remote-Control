package com.example.service;

import com.example.dto.LeaderboardEntry;
import com.example.dto.PointsDto;
import com.example.dto.UserPointsDto;
import com.example.entity.PointsHistory;
import com.example.entity.UserPoints;
import com.example.event.AnswerSavedEvent;
import com.example.event.SessionCompletedEvent;
import com.example.event.SessionStartedEvent;
import com.example.repository.PointsHistoryRepository;
import com.example.repository.UserPointsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamificationService {

    private final UserPointsRepository userPointsRepository;
    private final PointsHistoryRepository pointsHistoryRepository;
    private final RestTemplate restTemplate;

    @Value("${session.service.url:http://session-service:8080}")
    private String sessionServiceUrl;

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
        if (userId == null) return;

        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPoints up = new UserPoints();
                    up.setUserId(userId);
                    return up;
                });
        userPoints.setTotalPoints(userPoints.getTotalPoints() + pointsPerCorrectAnswer);
        userPointsRepository.save(userPoints);

        PointsHistory history = new PointsHistory();
        history.setUserId(userId);
        history.setSessionId(event.getSessionId());
        history.setTestId(event.getTestId());   // добавить
        history.setQuestionId(event.getQuestionId());
        history.setPointsEarned(pointsPerCorrectAnswer);
        pointsHistoryRepository.save(history);

        log.info("Awarded {} points to user {} for correct answer", pointsPerCorrectAnswer, userId);
    }

    @Transactional
    public void processSessionCompleted(SessionCompletedEvent event) {
        Long userId = event.getUserId();
        if (userId == null) return;

        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPoints up = new UserPoints();
                    up.setUserId(userId);
                    return up;
                });
        userPoints.setTotalPoints(userPoints.getTotalPoints() + pointsPerSessionCompletion);
        userPointsRepository.save(userPoints);

        PointsHistory history = new PointsHistory();
        history.setUserId(userId);
        history.setSessionId(event.getSessionId());
        history.setTestId(event.getTestId());   // добавить
        history.setPointsEarned(pointsPerSessionCompletion);
        pointsHistoryRepository.save(history);

        log.info("Awarded {} points to user {} for session completion", pointsPerSessionCompletion, userId);
    }

    public UserPointsDto getUserPoints(Long userId) {
        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPoints up = new UserPoints();
                    up.setUserId(userId);
                    up.setTotalPoints(0);
                    return up;
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


    public List<LeaderboardEntry> getLeaderboard(Long testId) {
        try {
            String url = sessionServiceUrl + "/api/sessions/leaderboard?testId=" + testId;
            ParameterizedTypeReference<List<LeaderboardEntry>> responseType =
                    new ParameterizedTypeReference<>() {};
            var response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            log.error("Failed to fetch leaderboard for testId {} from session-service", testId, e);
            return Collections.emptyList();
        }
    }
    public List<LeaderboardEntry> getTestLeaderboard(Long testId) {
        return pointsHistoryRepository.findTestLeaderboard(testId);
    }
    private final Set<Long> activeTestIds = ConcurrentHashMap.newKeySet();

    @EventListener
    public void onSessionStarted(SessionStartedEvent event) {
        activeTestIds.add(event.getTestId());
    }

    @EventListener
    public void onSessionCompleted(SessionCompletedEvent event) {
        // не удаляем сразу, можно через некоторое время, или хранить активные только те, где есть незавершённые сессии
        // для простоты можно не удалять
    }

    public List<Long> getActiveTestIds() {
        return new ArrayList<>(activeTestIds);
    }
}