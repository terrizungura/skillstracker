package com.example.skillsproject.services;

import com.example.skillsproject.dto.PersonSkillDto;
import com.example.skillsproject.entities.Person;
import com.example.skillsproject.entities.PersonSkill;
import com.example.skillsproject.entities.Skill;
import com.example.skillsproject.enums.SkillLevel;
import com.example.skillsproject.repositories.PersonRepository;
import com.example.skillsproject.repositories.PersonSkillRepository;
import com.example.skillsproject.repositories.SkillRepository;
import com.example.skillsproject.services.impl.PersonSkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonSkillServiceImplTest {

    @Mock
    private PersonSkillRepository personSkillRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private PersonSkillServiceImpl personSkillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSkillToPerson() {
        PersonSkillDto dto = new PersonSkillDto();
        dto.setPersonId(1L);
        dto.setSkillId(1L);
        dto.setLevel(SkillLevel.EXPERT);

        Person person = new Person();
        person.setId(1L);

        Skill skill = new Skill();
        skill.setId(1L);

        PersonSkill personSkill = new PersonSkill();
        personSkill.setPersonId(person.getId());
        personSkill.setSkillId(skill.getId());
        personSkill.setLevel(SkillLevel.EXPERT);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(personSkillRepository.save(any(PersonSkill.class))).thenReturn(personSkill);

        PersonSkillDto result = personSkillService.addSkillToPerson(dto);

        assertNotNull(result);
        assertEquals(1L, result.getPersonId());
        assertEquals(1L, result.getSkillId());
        assertEquals(SkillLevel.EXPERT, result.getLevel());

        verify(personRepository, times(1)).findById(1L);
        verify(skillRepository, times(1)).findById(1L);
        verify(personSkillRepository, times(1)).save(any(PersonSkill.class));
    }

}