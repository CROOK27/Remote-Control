package com.example.controller;

import com.example.dto.GroupDto;
import com.example.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Tag(name = "Groups", description = "Group management endpoints")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Get all groups")
    public ResponseEntity<List<GroupDto>> getAllGroups(
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/specialty/{specialtyId}")
    @Operation(summary = "Get groups by specialty")
    public ResponseEntity<List<GroupDto>> getGroupsBySpecialty(
            @PathVariable Long specialtyId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(groupService.getGroupsBySpecialty(specialtyId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by ID")
    public ResponseEntity<GroupDto> getGroupById(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    @Operation(summary = "Create new group")
    public ResponseEntity<GroupDto> createGroup(
            @RequestBody GroupDto groupDto,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"TEACHER".equals(userRole) && !"ADMIN".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(groupService.createGroup(groupDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update group")
    public ResponseEntity<GroupDto> updateGroup(
            @PathVariable Long id,
            @RequestBody GroupDto groupDto,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"TEACHER".equals(userRole) && !"ADMIN".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(groupService.updateGroup(id, groupDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete group")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"TEACHER".equals(userRole) && !"ADMIN".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}