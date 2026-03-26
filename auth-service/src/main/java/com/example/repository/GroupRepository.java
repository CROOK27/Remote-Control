package com.example.repository;


import com.example.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findBySpecialtyId(Long specialtyId);
    boolean existsByName(String name);
}
