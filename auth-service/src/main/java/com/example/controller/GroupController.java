package com.example.controller;

import com.example.dto.GroupDto;
import com.example.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/specialty/{specialtyId}")
    @Operation(summary = "Get groups by specialty")
    public ResponseEntity<List<GroupDto>> getGroupsBySpecialty(@PathVariable Long specialtyId) {
        return ResponseEntity.ok(groupService.getGroupsBySpecialty(specialtyId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by ID")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    @Operation(summary = "Create new group")
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.createGroup(groupDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update group")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete group")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}