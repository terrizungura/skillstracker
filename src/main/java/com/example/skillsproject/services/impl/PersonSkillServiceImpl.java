package com.example.skillsproject.services.impl;

import com.example.skillsproject.dto.PersonSkillDto;
import com.example.skillsproject.entities.Person;
import com.example.skillsproject.entities.PersonSkill;
import com.example.skillsproject.entities.Skill;
import com.example.skillsproject.enums.SkillLevel;
import com.example.skillsproject.exceptions.ResourceNotFoundException;
import com.example.skillsproject.repositories.PersonRepository;
import com.example.skillsproject.repositories.PersonSkillRepository;
import com.example.skillsproject.repositories.SkillRepository;
import com.example.skillsproject.services.PersonSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonSkillServiceImpl implements PersonSkillService {

    @Autowired
    PersonSkillRepository personSkillRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SkillRepository skillRepository;

    /**
     * @return
     */
    @Override
    @Transactional
    public List<PersonSkillDto> getAllPeopleSkills() {
        return personSkillRepository.findAll()
                .stream().map(this::convertToPersonSkillDto).toList();
    }

    private PersonSkillDto convertToPersonSkillDto(PersonSkill personSkill) {
        return new PersonSkillDto(
                personSkill.getPersonId(), personSkill.getSkillId(), personSkill.getLevel());
    }

    @Override
    @Transactional
    public PersonSkillDto addSkillToPerson(PersonSkillDto personSkillDto) {
        Person person = personRepository.findById(personSkillDto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personSkillDto.getPersonId()));
        Skill skill = skillRepository.findById(personSkillDto.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + personSkillDto.getSkillId()));

        PersonSkill personSkill = new PersonSkill();
        personSkill.setPersonId(person.getId());
        personSkill.setSkillId(skill.getId());
        personSkill.setLevel(personSkillDto.getLevel());

        PersonSkill savedPersonSkill = personSkillRepository.save(personSkill);
        return convertToPersonSkillDto(savedPersonSkill);
    }

    @Override
    @Transactional
    public PersonSkillDto updatePersonSkill(Long personId, Long skillId, PersonSkillDto personSkillDto) {
        PersonSkill personSkill = personSkillRepository.findByPersonIdAndSkillId(personId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("PersonSkill not found for person id: " + personId + " and skill id: " + skillId));

        personSkill.setLevel(personSkillDto.getLevel());
        PersonSkill updatedPersonSkill = personSkillRepository.save(personSkill);
        return convertToPersonSkillDto(updatedPersonSkill);
    }

    @Override
    @Transactional
    public List<PersonSkillDto> getPersonSkills(Long personId) {
        List<PersonSkill> personSkills = personSkillRepository.findByPersonId(personId);
        return personSkills.stream()
                .map(this::convertToPersonSkillDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public long getSkillCount(Long skillId) {
        return personSkillRepository.countBySkillId(skillId);
    }

    @Override
    @Transactional
    public List<PersonSkillDto> getPersonSkillsByLevel(Long personId, SkillLevel level) {
        return personSkillRepository.findByPersonIdAndLevel(personId, level)
                .stream()
                .map(this::convertToPersonSkillDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeSkillFromPerson(Long personId, Long skillId) {
        if (!personSkillRepository.existsByPersonIdAndSkillId(personId, skillId)) {
            throw new ResourceNotFoundException("PersonSkill not found for person id: " + personId + " and skill id: " + skillId);
        }
        personSkillRepository.deleteByPersonIdAndSkillId(personId, skillId);
    }
}
