package com.example.service;

import com.example.dto.QuestionDto;
import com.example.dto.OptionDto;
import com.example.dto.MatchingPairDto;
import com.example.entity.*;
import com.example.repository.OptionRepository;
import com.example.repository.MatchingPairRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final OptionRepository optionRepository;
    private final MatchingPairRepository matchingPairRepository;

    public List<QuestionDto> getQuestionsByTestId(Long testId) {
        return questionRepository.findByTestId(testId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public QuestionDto getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return convertToDto(question);
    }

    @Transactional
    public QuestionDto createQuestion(Long testId, QuestionDto dto) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        Question question = new Question();
        question.setTest(test);
        question.setText(dto.getText());
        question.setQuestionType(QuestionType.valueOf(dto.getQuestionType()));
        question.setPoints(dto.getPoints());
        question.setSortOrder(dto.getSortOrder());

        question = questionRepository.save(question);

        // Сохраняем варианты ответов
        if (dto.getOptions() != null) {
            for (OptionDto optDto : dto.getOptions()) {
                Option option = new Option();
                option.setQuestion(question);
                option.setText(optDto.getText());
                option.setIsCorrect(optDto.getIsCorrect());
                option.setSortOrder(optDto.getSortOrder());
                optionRepository.save(option);
            }
        }

        // Сохраняем пары для сопоставления
        if (dto.getMatchingPairs() != null) {
            for (MatchingPairDto pairDto : dto.getMatchingPairs()) {
                MatchingPair pair = new MatchingPair();
                pair.setQuestion(question);
                pair.setLeftItem(pairDto.getLeftItem());
                pair.setRightItem(pairDto.getRightItem());
                pair.setSortOrder(pairDto.getSortOrder());
                matchingPairRepository.save(pair);
            }
        }

        log.info("Created question for test: {}", testId);
        return convertToDto(question);
    }

    @Transactional
    public QuestionDto updateQuestion(Long id, QuestionDto dto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setText(dto.getText());
        question.setQuestionType(QuestionType.valueOf(dto.getQuestionType()));
        question.setPoints(dto.getPoints());
        question.setSortOrder(dto.getSortOrder());

        // Удаляем старые варианты и создаём новые
        optionRepository.deleteByQuestionId(id);
        matchingPairRepository.deleteByQuestionId(id);

        if (dto.getOptions() != null) {
            for (OptionDto optDto : dto.getOptions()) {
                Option option = new Option();
                option.setQuestion(question);
                option.setText(optDto.getText());
                option.setIsCorrect(optDto.getIsCorrect());
                option.setSortOrder(optDto.getSortOrder());
                optionRepository.save(option);
            }
        }

        if (dto.getMatchingPairs() != null) {
            for (MatchingPairDto pairDto : dto.getMatchingPairs()) {
                MatchingPair pair = new MatchingPair();
                pair.setQuestion(question);
                pair.setLeftItem(pairDto.getLeftItem());
                pair.setRightItem(pairDto.getRightItem());
                pair.setSortOrder(pairDto.getSortOrder());
                matchingPairRepository.save(pair);
            }
        }

        question = questionRepository.save(question);
        log.info("Updated question: {}", id);
        return convertToDto(question);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        questionRepository.delete(question);
        log.info("Deleted question: {}", id);
    }

    @Transactional
    public OptionDto addOption(Long questionId, OptionDto dto) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Option option = new Option();
        option.setQuestion(question);
        option.setText(dto.getText());
        option.setIsCorrect(dto.getIsCorrect());
        option.setSortOrder(dto.getSortOrder());

        option = optionRepository.save(option);
        log.info("Added option to question: {}", questionId);

        return convertToOptionDto(option);
    }

    @Transactional
    public OptionDto updateOption(Long id, OptionDto dto) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found"));

        option.setText(dto.getText());
        option.setIsCorrect(dto.getIsCorrect());
        option.setSortOrder(dto.getSortOrder());

        option = optionRepository.save(option);
        log.info("Updated option: {}", id);
        return convertToOptionDto(option);
    }

    @Transactional
    public void deleteOption(Long id) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found"));
        optionRepository.delete(option);
        log.info("Deleted option: {}", id);
    }

    @Transactional
    public MatchingPairDto addMatchingPair(Long questionId, MatchingPairDto dto) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        MatchingPair pair = new MatchingPair();
        pair.setQuestion(question);
        pair.setLeftItem(dto.getLeftItem());
        pair.setRightItem(dto.getRightItem());
        pair.setSortOrder(dto.getSortOrder());

        pair = matchingPairRepository.save(pair);
        log.info("Added matching pair to question: {}", questionId);

        return convertToMatchingPairDto(pair);
    }

    @Transactional
    public MatchingPairDto updateMatchingPair(Long id, MatchingPairDto dto) {
        MatchingPair pair = matchingPairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matching pair not found"));

        pair.setLeftItem(dto.getLeftItem());
        pair.setRightItem(dto.getRightItem());
        pair.setSortOrder(dto.getSortOrder());

        pair = matchingPairRepository.save(pair);
        log.info("Updated matching pair: {}", id);
        return convertToMatchingPairDto(pair);
    }

    @Transactional
    public void deleteMatchingPair(Long id) {
        MatchingPair pair = matchingPairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matching pair not found"));
        matchingPairRepository.delete(pair);
        log.info("Deleted matching pair: {}", id);
    }

    private QuestionDto convertToDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setQuestionType(question.getQuestionType().name());
        dto.setPoints(question.getPoints());
        dto.setSortOrder(question.getSortOrder());

        if (question.getOptions() != null) {
            dto.setOptions(question.getOptions().stream()
                    .map(this::convertToOptionDto)
                    .collect(Collectors.toList()));
        }

        if (question.getMatchingPairs() != null) {
            dto.setMatchingPairs(question.getMatchingPairs().stream()
                    .map(this::convertToMatchingPairDto)
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

    private MatchingPairDto convertToMatchingPairDto(MatchingPair pair) {
        return new MatchingPairDto(
                pair.getId(),
                pair.getLeftItem(),
                pair.getRightItem(),
                pair.getSortOrder()
        );
    }
}