package com.example.repository;



import com.example.entity.Role;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByGroupId(Long groupId);
    List<User> findByRoleAndGroupId(Role role, Long groupId);
}