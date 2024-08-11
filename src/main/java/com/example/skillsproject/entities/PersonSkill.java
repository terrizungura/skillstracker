package com.example.skillsproject.entities;

import com.example.skillsproject.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "personId", nullable = false)
    private Long personId;

    @Column(name = "skillId", nullable = false)
    private Long skillId;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;
}
