package com.example.service;


import com.example.dto.TeacherDto;
import com.example.entity.Teacher;
import com.example.entity.User;
import com.example.repository.TeacherRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TeacherDto getTeacherByUserId(Long userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return convertToDto(teacher);
    }

    @Transactional
    public TeacherDto createTeacher(TeacherDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (teacherRepository.existsByUserId(dto.getUserId())) {
            throw new RuntimeException("Teacher already exists for this user");
        }

        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setDepartment(dto.getDepartment());
        teacher.setPosition(dto.getPosition());
        teacher.setHireDate(dto.getHireDate());

        teacher = teacherRepository.save(teacher);
        return convertToDto(teacher);
    }

    @Transactional
    public TeacherDto updateTeacher(Long userId, TeacherDto dto) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setDepartment(dto.getDepartment());
        teacher.setPosition(dto.getPosition());
        teacher.setHireDate(dto.getHireDate());

        teacher = teacherRepository.save(teacher);
        return convertToDto(teacher);
    }

    @Transactional
    public void deleteTeacher(Long userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacherRepository.delete(teacher);
    }

    private TeacherDto convertToDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setUserId(teacher.getUser().getId());
        dto.setUsername(teacher.getUser().getUsername());
        dto.setFirstName(teacher.getUser().getFirstName());
        dto.setLastName(teacher.getUser().getLastName());
        dto.setEmail(teacher.getUser().getEmail());
        dto.setDepartment(teacher.getDepartment());
        dto.setPosition(teacher.getPosition());
        dto.setHireDate(teacher.getHireDate());
        return dto;
    }
}
