package com.example.service;


import com.example.dto.GroupDto;
import com.example.entity.Group;
import com.example.entity.Specialty;
import com.example.repository.GroupRepository;
import com.example.repository.SpecialtyRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final SpecialtyRepository specialtyRepository;
    private final UserRepository userRepository;

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GroupDto> getGroupsBySpecialty(Long specialtyId) {
        return groupRepository.findBySpecialtyId(specialtyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public GroupDto getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return convertToDto(group);
    }

    @Transactional
    public GroupDto createGroup(GroupDto dto) {
        if (groupRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Group name already exists");
        }

        Group group = new Group();
        group.setName(dto.getName());
        group.setYearOfStudy(dto.getYearOfStudy());
        group.setAcademicYear(dto.getAcademicYear());

        if (dto.getSpecialtyId() != null) {
            Specialty specialty = specialtyRepository.findById(dto.getSpecialtyId())
                    .orElseThrow(() -> new RuntimeException("Specialty not found"));
            group.setSpecialty(specialty);
        }

        group = groupRepository.save(group);
        return convertToDto(group);
    }

    @Transactional
    public GroupDto updateGroup(Long id, GroupDto dto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        group.setName(dto.getName());
        group.setYearOfStudy(dto.getYearOfStudy());
        group.setAcademicYear(dto.getAcademicYear());

        if (dto.getSpecialtyId() != null) {
            Specialty specialty = specialtyRepository.findById(dto.getSpecialtyId())
                    .orElseThrow(() -> new RuntimeException("Specialty not found"));
            group.setSpecialty(specialty);
        }

        group = groupRepository.save(group);
        return convertToDto(group);
    }

    @Transactional
    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Проверяем, есть ли студенты в группе
        long studentCount = userRepository.findByGroupId(id).size();
        if (studentCount > 0) {
            throw new RuntimeException("Cannot delete group with students");
        }

        groupRepository.delete(group);
    }

    private GroupDto convertToDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setYearOfStudy(group.getYearOfStudy());
        dto.setAcademicYear(group.getAcademicYear());
        dto.setStudentCount(userRepository.findByGroupId(group.getId()).size());

        if (group.getSpecialty() != null) {
            dto.setSpecialtyId(group.getSpecialty().getId());
            dto.setSpecialtyName(group.getSpecialty().getName());
        }

        return dto;
    }
}
