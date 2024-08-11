package com.example.skillsproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skillId")
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "PersonSkills",
            joinColumns = {@JoinColumn(name = "skillId")},
            inverseJoinColumns = {@JoinColumn(name = "personId")})
    private Person person;
}
