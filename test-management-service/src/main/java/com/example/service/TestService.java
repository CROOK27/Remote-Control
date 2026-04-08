package com.example.service;


import com.example.dto.CreateTestRequest;
import com.example.dto.OptionDto;
import com.example.dto.QuestionDto;
import com.example.dto.TestDto;
import com.example.entity.*;
import com.example.producer.TestEventProducer;
import com.example.repository.OptionRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final TestEventProducer eventProducer;

    public List<TestDto> getAllTests() {
        return testRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TestDto> getTestsByTeacher(Long teacherId) {
        return testRepository.findByTeacherId(teacherId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TestDto> getPublishedTests() {
        return testRepository.findByStatus(TestStatus.PUBLISHED).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TestDto getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
        return convertToDto(test);
    }

    @Transactional
    public TestDto createTest(CreateTestRequest request) {
        Test test = new Test();
        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        test.setTeacherId(request.getTeacherId());
        test.setAttemptsAllowed(request.getAttemptsAllowed());
        test.setShuffleQuestions(request.getShuffleQuestions());
        test.setShuffleOptions(request.getShuffleOptions());

        test = testRepository.save(test);

        if (request.getQuestions() != null) {
            for (QuestionDto qDto : request.getQuestions()) {
                Question question = createQuestion(test, qDto);
                questionRepository.save(question);
            }
        }

        log.info("Created test: {} by teacher: {}", test.getId(), test.getTeacherId());
        return convertToDto(test);
    }

    @Transactional
    public TestDto updateTest(Long id, CreateTestRequest request) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        test.setAttemptsAllowed(request.getAttemptsAllowed());
        test.setShuffleQuestions(request.getShuffleQuestions());
        test.setShuffleOptions(request.getShuffleOptions());
        test.setUpdatedAt(LocalDateTime.now());

        // Удаляем старые вопросы и создаём новые
        questionRepository.deleteByTestId(id);

        if (request.getQuestions() != null) {
            for (QuestionDto qDto : request.getQuestions()) {
                Question question = createQuestion(test, qDto);
                questionRepository.save(question);
            }
        }

        test = testRepository.save(test);
        log.info("Updated test: {}", test.getId());
        return convertToDto(test);
    }

    @Transactional
    public TestDto publishTest(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        test.setStatus(TestStatus.PUBLISHED);
        test.setUpdatedAt(LocalDateTime.now());
        test = testRepository.save(test);

        // Отправляем событие в Kafka
        eventProducer.sendTestPublishedEvent(test.getId(), test.getTeacherId());

        log.info("Published test: {}", test.getId());
        return convertToDto(test);
    }

    @Transactional
    public void deleteTest(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        testRepository.delete(test);
        log.info("Deleted test: {}", id);
    }

    private Question createQuestion(Test test, QuestionDto dto) {
        Question question = new Question();
        question.setTest(test);
        question.setText(dto.getText());
        question.setQuestionType(QuestionType.valueOf(dto.getQuestionType()));
        question.setPoints(dto.getPoints());
        question.setSortOrder(dto.getSortOrder());

        if (dto.getOptions() != null) {
            for (OptionDto oDto : dto.getOptions()) {
                Option option = new Option();
                option.setQuestion(question);
                option.setText(oDto.getText());
                option.setIsCorrect(oDto.getIsCorrect());
                option.setSortOrder(oDto.getSortOrder());
                question.getOptions().add(option);
            }
        }

        return question;
    }

    private TestDto convertToDto(Test test) {
        TestDto dto = new TestDto();
        dto.setId(test.getId());
        dto.setTitle(test.getTitle());
        dto.setDescription(test.getDescription());
        dto.setTeacherId(test.getTeacherId());
        dto.setTimeLimitMinutes(test.getTimeLimitMinutes());
        dto.setAttemptsAllowed(test.getAttemptsAllowed());
        dto.setShuffleQuestions(test.getShuffleQuestions());
        dto.setShuffleOptions(test.getShuffleOptions());
        dto.setStatus(test.getStatus().name());
        dto.setCreatedAt(test.getCreatedAt());
        dto.setUpdatedAt(test.getUpdatedAt());

        if (test.getQuestions() != null) {
            dto.setQuestions(test.getQuestions().stream()
                    .map(this::convertToQuestionDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private QuestionDto convertToQuestionDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setQuestionType(String.valueOf(question.getQuestionType()));
        dto.setPoints(question.getPoints());
        dto.setSortOrder(question.getSortOrder());

        if (question.getOptions() != null) {
            dto.setOptions(question.getOptions().stream()
                    .map(this::convertToOptionDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private OptionDto convertToOptionDto(Option option) {
        return new OptionDto(
                option.getId(),
                option.getText(),
                option.getIsCorrect(),
                option.getSortOrder()
        );
    }
}
