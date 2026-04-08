package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Column(name = "year_of_study")
    private Integer yearOfStudy;

    @Column(name = "academic_year")
    private String academicYear;

    @OneToMany(mappedBy = "group")
    @Column(nullable = false, columnDefinition = "0")
    private List<User> students = new ArrayList<>();
}