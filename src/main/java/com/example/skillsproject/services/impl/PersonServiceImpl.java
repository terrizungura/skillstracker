package com.example.skillsproject.services.impl;

import com.example.skillsproject.dto.PersonDto;
import com.example.skillsproject.dto.SkillDto;
import com.example.skillsproject.entities.Person;
import com.example.skillsproject.entities.Skill;
import com.example.skillsproject.exceptions.ResourceNotFoundException;
import com.example.skillsproject.repositories.PersonRepository;
import com.example.skillsproject.services.PersonService;
import com.example.skillsproject.services.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SkillService skillService;

    /**
     * Retrieves a paginated list of all people.
     *
     * @param pageable the pagination information, which includes page number and size.
     * @return a page of `PersonDto` objects, representing all people with pagination applied.
     */
    @Override
    public Page<PersonDto> getAllPeople(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(this::convertToPersonDto);
    }

    private PersonDto convertToPersonDto(Person person) {
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());

        if (person.getSkills() != null) {
            Set<SkillDto> skillDtos = person.getSkills().stream()
                    .map(this::convertToSkillsDto).collect(Collectors.toSet());

            dto.setSkills(skillDtos);
        }


        return dto;
    }

    private SkillDto convertToSkillsDto(Skill skill) {
        return new SkillDto(skill.getId(), skill.getName());
    }

    /**
     * Retrieves a person by their ID.
     *
     * @param id the ID of the person to retrieve.
     * @return a `PersonDto` object representing the person with the specified ID.
     * @throws ResourceNotFoundException if no person is found with the given ID.
     */
    @Override
    public PersonDto getPerson(Long id) {
        return personRepository.findById(id)
                .map(this::convertToPersonDto)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for id: " + id));
    }

    /**
     * Creates a new person.
     *
     * @param personDto the data transfer object containing information of the person to create.
     * @return a `PersonDto` object representing the created person.
     * @throws Exception if any error occurs during the creation process.
     */
    @Override
    @Transactional
    public PersonDto createPerson(PersonDto personDto) {
        try {
            log.info("Creating person: {} ", personDto);
            Person person = new Person();
            person.setName(personDto.getName());
            person.setEmail(personDto.getEmail());
            Person savedPerson = personRepository.save(person);
            log.info("Person saved successfully: {} ", savedPerson);
            return convertToPersonDto(savedPerson);
        } catch (Exception e) {
            log.error("Error creating person: ", e);
            throw e;
        }
    }

    /**
     * Updates an existing person.
     *
     * @param id        the ID of the person to update.
     * @param personDto the data transfer object containing updated information for the person.
     * @return a `PersonDto` object representing the updated person.
     * @throws ResourceNotFoundException if no person is found with the given ID.
     */
    @Override
    @Transactional
    public PersonDto updatePerson(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        person.setName(personDto.getName());
        person.setEmail(personDto.getEmail());
        Person updatedPerson = personRepository.save(person);
        return convertToPersonDto(updatedPerson);
    }

    /**
     * Deletes a person by their ID.
     *
     * @param id the ID of the person to delete.
     * @throws ResourceNotFoundException if no person is found with the given ID.
     */
    @Override
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        personRepository.delete(person);
    }
}
