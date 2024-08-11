package com.example.skillsproject.dto;

import com.example.skillsproject.enums.SkillLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonSkillDto {

    @NotNull(message = "Person ID cannot be null")
    private Long personId;

    @NotNull(message = "Skill ID cannot be null")
    private Long skillId;

    @NotNull(message = "Skill level cannot be null")
    private SkillLevel level;
}
