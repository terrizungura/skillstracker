package com.example.skillsproject.services;

import com.example.skillsproject.dto.PersonDto;
import com.example.skillsproject.entities.Person;
import com.example.skillsproject.exceptions.ResourceNotFoundException;
import com.example.skillsproject.repositories.PersonRepository;
import com.example.skillsproject.services.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePerson() {
        PersonDto personDto = new PersonDto();
        personDto.setName("John Doe");
        personDto.setEmail("john@example.com");

        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setEmail("john@example.com");

        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto result = personService.createPerson(personDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());

        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testGetPerson() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setEmail("john@example.com");

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        PersonDto result = personService.getPerson(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());

        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personService.getPerson(1L));

        verify(personRepository, times(1)).findById(1L);
    }

    @ParameterizedTest
    @CsvSource({
            "John Doe, john.doe@example.com",
            "Jane Smith, jane.smith@example.com"
    })
    @Transactional
    void testCreatePerson(String name, String email) {
        // Given
        PersonDto personDto = new PersonDto();
        personDto.setName(name);
        personDto.setEmail(email);

        Person person = new Person();
        person.setName(name);
        person.setEmail(email);

        Person savedPerson = new Person();
        savedPerson.setId(1L);
        savedPerson.setName(name);
        savedPerson.setEmail(email);

        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        // When
        PersonDto result = personService.createPerson(personDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        verify(personRepository, times(1)).save(any(Person.class));
    }
}