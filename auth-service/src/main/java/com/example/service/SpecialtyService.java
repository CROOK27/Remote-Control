package com.example.service;


import com.example.dto.SpecialtyDto;
import com.example.entity.Specialty;
import com.example.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public List<SpecialtyDto> getAllSpecialties() {
        return specialtyRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SpecialtyDto getSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
        return convertToDto(specialty);
    }

    @Transactional
    public SpecialtyDto createSpecialty(SpecialtyDto dto) {
        if (specialtyRepository.existsByCode(dto.getCode())) {
            throw new RuntimeException("Specialty code already exists");
        }

        Specialty specialty = new Specialty();
        specialty.setCode(dto.getCode());
        specialty.setName(dto.getName());
        specialty.setDescription(dto.getDescription());

        specialty = specialtyRepository.save(specialty);
        return convertToDto(specialty);
    }

    @Transactional
    public SpecialtyDto updateSpecialty(Long id, SpecialtyDto dto) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));

        specialty.setName(dto.getName());
        specialty.setDescription(dto.getDescription());

        specialty = specialtyRepository.save(specialty);
        return convertToDto(specialty);
    }

    @Transactional
    public void deleteSpecialty(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
        specialtyRepository.delete(specialty);
    }

    private SpecialtyDto convertToDto(Specialty specialty) {
        SpecialtyDto dto = new SpecialtyDto();
        dto.setId(specialty.getId());
        dto.setCode(specialty.getCode());
        dto.setName(specialty.getName());
        dto.setDescription(specialty.getDescription());
        return dto;
    }
}
