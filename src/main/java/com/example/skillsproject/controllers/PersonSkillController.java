package com.example.skillsproject.controllers;

import com.example.skillsproject.dto.PersonSkillDto;
import com.example.skillsproject.enums.SkillLevel;
import com.example.skillsproject.services.PersonSkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personskills")
@Validated
@Tag(name = "PersonSkill", description = "Endpoints for Managing Persons and their respective skills")
public class PersonSkillController {

    @Autowired
    private PersonSkillService personSkillService;

    @Operation(summary = "Assign skill to a person", description = "Make a new assignment of a skill to a person", tags = {"PersonSkill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully assign the skill"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping("/addskilltoperson")
    public ResponseEntity<PersonSkillDto> addSkillToPerson(@Valid @RequestBody PersonSkillDto personSkillDto) {
        PersonSkillDto createdPersonSkill = personSkillService.addSkillToPerson(personSkillDto);
        return new ResponseEntity<>(createdPersonSkill, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a person's skill", description = "Update the skill of a person", tags = {"PersonSkill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the skill"),
            @ApiResponse(responseCode = "404", description = "Person or Skill not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PutMapping("/{personId}/{skillId}")
    public ResponseEntity<PersonSkillDto> updatePersonSkill(
            @PathVariable Long personId,
            @PathVariable Long skillId,
            @Valid @RequestBody PersonSkillDto personSkillDto) {
        PersonSkillDto updatedPersonSkill = personSkillService.updatePersonSkill(personId, skillId, personSkillDto);
        return ResponseEntity.ok(updatedPersonSkill);

    }

    @Operation(summary = "Remove a skill from a person", description = "Delete the assignment of a skill from a person", tags = {"PersonSkill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed the skill"),
            @ApiResponse(responseCode = "404", description = "Person or Skill not found")
    })
    @DeleteMapping("/{personId}/{skillId}")
    public ResponseEntity<Void> removeSkillFromPerson(
            @PathVariable Long personId,
            @PathVariable Long skillId) {
        personSkillService.removeSkillFromPerson(personId, skillId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all skills of a person", description = "Retrieve all skills assigned to a specific person", tags = {"PersonSkill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the skills"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<PersonSkillDto>> getPersonSkills(@PathVariable Long personId) {
        List<PersonSkillDto> personSkills = personSkillService.getPersonSkills(personId);
        return ResponseEntity.ok(personSkills);
    }

    @Operation(summary = "Get all Personskills", description = "Get the list of all skills vs persons in the organization", tags = {"Personskills"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PersonSkillDto>> getAllPersonSkills() {
        List<PersonSkillDto> personSkills = personSkillService.getAllPeopleSkills();
        return ResponseEntity.ok(personSkills);
    }


    @Operation(summary = "Get skills of a person by level", description = "Retrieve all skills assigned to a specific person at a given level", tags = {"PersonSkill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the skills"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/person/{personId}/level/{level}")
    public ResponseEntity<List<PersonSkillDto>> getPersonSkillsByLevel(
            @PathVariable Long personId,
            @PathVariable SkillLevel level) {
        List<PersonSkillDto> personSkills = personSkillService.getPersonSkillsByLevel(personId, level);
        return ResponseEntity.ok(personSkills);
    }

    @Operation(summary = "Get count of people with a specific skill", description = "Retrieve the number of people who have a specific skill", tags = {"PersonSkill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the count"),
            @ApiResponse(responseCode = "404", description = "Skill not found")
    })
    @GetMapping("/skill/{skillId}/count")
    public ResponseEntity<Long> getSkillCount(@PathVariable Long skillId) {
        long count = personSkillService.getSkillCount(skillId);
        return ResponseEntity.ok(count);
    }

}
