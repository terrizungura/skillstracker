package com.example.skillsproject.services;

import com.example.skillsproject.dto.SkillDto;
import com.example.skillsproject.entities.Skill;
import com.example.skillsproject.repositories.SkillRepository;
import com.example.skillsproject.services.impl.SkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SkillServiceImplTest {

    @InjectMocks
    private SkillServiceImpl skillService;

    @Mock
    private SkillRepository skillRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @ParameterizedTest
    @CsvSource({
            "1, Java",
            "2, Python"
    })
    @Transactional
    void testCreateSkill(Long id, String name) {
        // Given
        SkillDto skillDto = new SkillDto();
        skillDto.setName(name);

        Skill skill = new Skill();
        skill.setName(name);

        Skill savedSkill = new Skill();
        savedSkill.setId(id);
        savedSkill.setName(name);

        when(skillRepository.save(any(Skill.class))).thenReturn(savedSkill);

        // When
        SkillDto result = skillService.createSkill(skillDto);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        verify(skillRepository, times(1)).save(any(Skill.class));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Java",
            "2, Python"
    })
    @Transactional
    void testUpdateSkill(Long id, String name) {
        // Given
        SkillDto skillDto = new SkillDto();
        skillDto.setName(name);

        Skill existingSkill = new Skill();
        existingSkill.setId(id);
        existingSkill.setName("Old Name");

        Skill updatedSkill = new Skill();
        updatedSkill.setId(id);
        updatedSkill.setName(name);

        when(skillRepository.findById(id)).thenReturn(Optional.of(existingSkill));
        when(skillRepository.save(any(Skill.class))).thenReturn(updatedSkill);

        // When
        SkillDto result = skillService.updateSkill(id, skillDto);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        verify(skillRepository, times(1)).findById(id);
        verify(skillRepository, times(1)).save(existingSkill);
    }
}
