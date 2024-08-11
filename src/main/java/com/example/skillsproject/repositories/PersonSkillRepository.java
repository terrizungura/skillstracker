package com.example.skillsproject.repositories;

import com.example.skillsproject.entities.PersonSkill;
import com.example.skillsproject.enums.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonSkillRepository extends JpaRepository<PersonSkill, Long> {

    /**
     * Find a PersonSkill by personId and skillId
     */
    Optional<PersonSkill> findByPersonIdAndSkillId(Long personId, Long skillId);

    /**
     * Find all PersonSkills for a given personId
     */
    List<PersonSkill> findByPersonId(Long personId);

    /**
     * Delete a PersonSkill by personId and skillId
     */
    void deleteByPersonIdAndSkillId(Long personId, Long skillId);

    /**
     * Check if a PersonSkill exists for a given personId and skillId
     */
    boolean existsByPersonIdAndSkillId(Long personId, Long skillId);

    /**
     * Count the number of people who have a particular skill
     */
    long countBySkillId(Long skillId);

    /**
     * Find all PersonSkills for a given personId and skill level
     */
    List<PersonSkill> findByPersonIdAndLevel(Long personId, SkillLevel level);

}
