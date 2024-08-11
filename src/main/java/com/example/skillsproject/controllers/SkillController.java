package com.example.skillsproject.controllers;

import com.example.skillsproject.dto.SkillDto;
import com.example.skillsproject.services.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
@Validated
@Tag(name = "Skill", description = "Endpoints for Managing skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Operation(summary = "Get all skills", description = "Get the list of all skills in the organization", tags = {"Skill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<Page<SkillDto>> getAllSkills(Pageable pageable) {
        return new ResponseEntity<>(skillService.getAllSkills(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get a skill by ID", description = "Retrieve details of a specific skill by its ID", tags = {"Skill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the skill details"),
            @ApiResponse(responseCode = "404", description = "Skill not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SkillDto> getSkill(@PathVariable Long id) {
        return new ResponseEntity<>(skillService.getSkill(id), HttpStatus.OK);
    }

    @Operation(summary = "Creates a new skill", description = "Add a new skill to the organization", tags = {"Skill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the skill"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping("/create")
    public ResponseEntity<SkillDto> createSkill(@Valid @RequestBody SkillDto skillDto) {
        return new ResponseEntity<>(skillService.createSkill(skillDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing skill", description = "Update details of an existing skill by ID", tags = {"Skill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the skill"),
            @ApiResponse(responseCode = "404", description = "Skill not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillDto skillDto) {

        return new ResponseEntity<>(skillService.updateSkill(id, skillDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete a skill by ID", description = "Delete a skill from the organization by its ID", tags = {"Skill"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the skill"),
            @ApiResponse(responseCode = "404", description = "Skill not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
