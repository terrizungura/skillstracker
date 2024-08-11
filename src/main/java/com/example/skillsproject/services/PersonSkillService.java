package com.example.skillsproject.services;

import com.example.skillsproject.dto.PersonSkillDto;
import com.example.skillsproject.enums.SkillLevel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonSkillService {

    public List<PersonSkillDto> getAllPeopleSkills();

    @Transactional
    PersonSkillDto addSkillToPerson(PersonSkillDto personSkillDto);

    @Transactional
    PersonSkillDto updatePersonSkill(Long personId, Long skillId, PersonSkillDto personSkillDto);

    List<PersonSkillDto> getPersonSkills(Long personId);

    long getSkillCount(Long skillId);

    List<PersonSkillDto> getPersonSkillsByLevel(Long personId, SkillLevel level);

    @Transactional
    void removeSkillFromPerson(Long personId, Long skillId);


}
