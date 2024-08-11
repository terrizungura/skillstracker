package com.example.skillsproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personId")
    private Long id;
    private String name;
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PersonSkill",
            joinColumns = {@JoinColumn(name = "personId")},
            inverseJoinColumns = {@JoinColumn(name = "skillId")})
    private Set<Skill> skills;
}
