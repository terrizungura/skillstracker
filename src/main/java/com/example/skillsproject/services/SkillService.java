package com.example.skillsproject.services;

import com.example.skillsproject.dto.SkillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface SkillService {

    public Page<SkillDto> getAllSkills(Pageable pageable);

    public SkillDto getSkill(Long id);

    public SkillDto createSkill(SkillDto skillDto);

    @Transactional
    SkillDto updateSkill(Long id, SkillDto skillDto);

    public void deleteSkill(Long id);
}
