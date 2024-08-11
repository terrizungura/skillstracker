package com.example.skillsproject.controllers;

import com.example.skillsproject.dto.PersonDto;
import com.example.skillsproject.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/persons")
@Validated
@Tag(name = "Person", description = "Endpoints for Managing people")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Operation(summary = "Get all people", description = "Get the list of all people in the organization", tags = {"Person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<PersonDto>> getAllPeople(Pageable pageable) {
        return new ResponseEntity<>(personService.getAllPeople(pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id) {
        return new ResponseEntity<>(personService.getPerson(id), HttpStatus.OK);
    }

    @Operation(summary = "Creates a new person", description = "Add a new person to the organization", tags = {"Person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the skill"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping("/create")
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto personDto) {
        log.info("Creating person {} ", personDto.getName());
        PersonDto createdPerson = personService.createPerson(personDto);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);

    }

    @Operation(summary = "Update an existing person", description = "Update details of an existing person by ID", tags = {"Person"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the person"),
            @ApiResponse(responseCode = "404", description = "Person not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PutMapping("{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonDto personDto) {
        PersonDto updatedPerson = personService.updatePerson(id, personDto);
        return new ResponseEntity<>(updatedPerson, HttpStatus.CREATED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.info("Deleting person {} ", id);
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
