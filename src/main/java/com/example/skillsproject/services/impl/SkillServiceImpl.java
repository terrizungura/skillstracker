package com.example.skillsproject.services.impl;

import com.example.skillsproject.dto.SkillDto;
import com.example.skillsproject.entities.Skill;
import com.example.skillsproject.exceptions.ResourceNotFoundException;
import com.example.skillsproject.repositories.SkillRepository;
import com.example.skillsproject.services.SkillService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    SkillRepository skillRepository;

    /**
     * Retrieves a paginated list of all people.
     *
     * @param pageable the pagination information, which includes page number and size.
     * @return a page of `SkillDto` objects, representing all skills with pagination applied.
     */
    @Override
    public Page<SkillDto> getAllSkills(Pageable pageable) {

        return skillRepository.findAll(pageable)
                .map(this::convertToSkillsDto);
    }

    private SkillDto convertToSkillsDto(Skill skill) {
        return new SkillDto(skill.getId(), skill.getName());
    }

    /**
     * @param id The ID of the skill to retrieve.
     * @return A SkillDto representing the found skill.
     * @throws ResourceNotFoundException if the skill is not found.
     */
    @Override
    public SkillDto getSkill(Long id) {
        return skillRepository.findById(id)
                .map(this::convertToSkillsDto)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found for id:" + id));
    }

    /**
     * @param skillDto the skill to be created
     * @return skillDto the created skill
     * @throws ResourceNotFoundException if the skill is not found.
     */
    @Override
    @Transactional
    public SkillDto createSkill(SkillDto skillDto) {
        Skill newSkill = new Skill();
        newSkill.setName(skillDto.getName());
        return convertToSkillsDto(skillRepository.save(newSkill));
    }

    @Override
    @Transactional
    public SkillDto updateSkill(Long id, SkillDto skillDto) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        skill.setName(skillDto.getName());
        Skill updatedSkill = skillRepository.save(skill);
        return convertToSkillsDto(updatedSkill);
    }

    /**
     * @param id of the skill to be deleted
     */
    @Override
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found with id: " + id));
        skillRepository.delete(skill);
    }
}
