package com.example.skillsproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Long id;

    @NotNull(message = "Person name cannot be null")
    @Size(max = 100, min = 2)
    private String name;

    @NotBlank(message = "email cannot be black")
    @Email(message = "Email should ne valid")
    private String email;

    private Set<SkillDto> skills;
}
