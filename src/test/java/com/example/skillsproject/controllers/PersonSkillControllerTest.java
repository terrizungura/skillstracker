package com.example.skillsproject.controllers;

import com.example.skillsproject.dto.PersonDto;
import com.example.skillsproject.dto.PersonSkillDto;
import com.example.skillsproject.dto.SkillDto;
import com.example.skillsproject.enums.SkillLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonSkillControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PersonSkillControllerTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    private Long personId;
    private Long skillId;

    @BeforeEach
    void setup() {
        // Create a person
        PersonDto personDto = new PersonDto();
        personDto.setName("Test Person");
        personDto.setEmail("test@example.com");
        ResponseEntity<PersonDto> personResponse = restTemplate.postForEntity("/api/persons/create", personDto, PersonDto.class);
        assertEquals(HttpStatus.CREATED, personResponse.getStatusCode());
        assertNotNull(personResponse.getBody());
        this.personId = personResponse.getBody().getId();

        // Create a skill
        SkillDto skillDto = new SkillDto();
        skillDto.setName("Test Skill");
        ResponseEntity<SkillDto> skillResponse = restTemplate.postForEntity("/api/skills/create", skillDto, SkillDto.class);
        assertEquals(HttpStatus.CREATED, skillResponse.getStatusCode());
        assertNotNull(skillResponse.getBody());
        this.skillId = skillResponse.getBody().getId();

        // Add skill to person
        PersonSkillDto dto = new PersonSkillDto();
        dto.setPersonId(this.personId);
        dto.setSkillId(this.skillId);
        dto.setLevel(SkillLevel.PRACTITIONER);
        ResponseEntity<PersonSkillDto> addSkillResponse = restTemplate.postForEntity("/api/personskills/addskilltoperson", dto, PersonSkillDto.class);
        assertEquals(HttpStatus.CREATED, addSkillResponse.getStatusCode());

    }

    @Test
    void testAddSkillToPerson() {
        PersonSkillDto dto = new PersonSkillDto();
        dto.setPersonId(1L);
        dto.setSkillId(1L);
        dto.setLevel(SkillLevel.EXPERT);

        ResponseEntity<PersonSkillDto> response = restTemplate.postForEntity("/api/personskills/addskilltoperson", dto, PersonSkillDto.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            logger.error("Unexpected status code: " + response.getStatusCode());
            logger.error("Response body: " + response.getBody());
        }

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getPersonId());
        assertEquals(1L, response.getBody().getSkillId());
        assertEquals(SkillLevel.EXPERT, response.getBody().getLevel());
    }

    @Test
    void testCreatePerson() {
        PersonDto personDto = new PersonDto();
        personDto.setName("Test Person");
        personDto.setEmail("test@example.com");

        ResponseEntity<PersonDto> response = restTemplate.postForEntity("/api/persons/create", personDto, PersonDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Person", response.getBody().getName());
        assertEquals("test@example.com", response.getBody().getEmail());
    }

    @Test
    void testUpdatePersonSkill() {
        PersonSkillDto dto = new PersonSkillDto();
        dto.setPersonId(this.personId);
        dto.setSkillId(this.skillId);
        dto.setLevel(SkillLevel.PRACTITIONER);

        ResponseEntity<PersonSkillDto> response = restTemplate.exchange(
                "/api/personskills/" + this.personId + "/" + this.skillId,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                PersonSkillDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(this.personId, response.getBody().getPersonId());
        assertEquals(this.skillId, response.getBody().getSkillId());
        assertEquals(SkillLevel.PRACTITIONER, response.getBody().getLevel());
    }

    @Test
    void testRemoveSkillFromPerson() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/personskills/1/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetPersonSkills() {
        ResponseEntity<PersonSkillDto[]> response = restTemplate.getForEntity(
                "/api/personskills/person/" + this.personId,
                PersonSkillDto[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals(this.skillId, response.getBody()[0].getSkillId());
    }
}