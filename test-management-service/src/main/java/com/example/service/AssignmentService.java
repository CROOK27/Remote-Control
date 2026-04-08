package com.example.service;

import com.example.dto.AssignmentDto;
import com.example.entity.TestAssignment;
import com.example.repository.TestAssignmentRepository;
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
public class AssignmentService {

    private final TestAssignmentRepository assignmentRepository;
    private final TestRepository testRepository;

    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AssignmentDto> getAssignmentsByTestId(Long testId) {
        return assignmentRepository.findByTestId(testId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AssignmentDto> getAssignmentsByGroupId(Long groupId) {
        return assignmentRepository.findByGroupId(groupId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AssignmentDto createAssignment(AssignmentDto dto) {
        // Проверяем, существует ли тест
        testRepository.findById(dto.getTestId())
                .orElseThrow(() -> new RuntimeException("Test not found"));

        // Проверяем, нет ли уже назначения
        if (assignmentRepository.findByTestIdAndGroupId(dto.getTestId(), dto.getGroupId()).isPresent()) {
            throw new RuntimeException("Assignment already exists for this test and group");
        }

        TestAssignment assignment = new TestAssignment();
        assignment.setTestId(dto.getTestId());
        assignment.setGroupId(dto.getGroupId());
        assignment.setStartDate(dto.getStartDate());
        assignment.setEndDate(dto.getEndDate());
        assignment.setActive(dto.getActive());

        assignment = assignmentRepository.save(assignment);
        log.info("Created assignment: test {} -> group {}", dto.getTestId(), dto.getGroupId());

        return convertToDto(assignment);
    }

    @Transactional
    public AssignmentDto updateAssignment(Long id, AssignmentDto dto) {
        TestAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setStartDate(dto.getStartDate());
        assignment.setEndDate(dto.getEndDate());
        assignment.setActive(dto.getActive());

        assignment = assignmentRepository.save(assignment);
        log.info("Updated assignment: {}", id);

        return convertToDto(assignment);
    }

    @Transactional
    public void deleteAssignment(Long id) {
        TestAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignmentRepository.delete(assignment);
        log.info("Deleted assignment: {}", id);
    }

    private AssignmentDto convertToDto(TestAssignment assignment) {
        AssignmentDto dto = new AssignmentDto();
        dto.setId(assignment.getId());
        dto.setTestId(assignment.getTestId());
        dto.setGroupId(assignment.getGroupId());
        dto.setStartDate(assignment.getStartDate());
        dto.setEndDate(assignment.getEndDate());
        dto.setActive(assignment.getActive());
        dto.setCreatedAt(assignment.getCreatedAt());

        // Загружаем название теста
        testRepository.findById(assignment.getTestId()).ifPresent(test ->
                dto.setTestTitle(test.getTitle()));

        return dto;
    }
}
