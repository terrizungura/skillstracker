package com.example.skillsproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {

    private Long id;

    @NotNull(message = "Skill name cannot be null")
    @Size(max = 100, min = 2)
    private String name;
}
