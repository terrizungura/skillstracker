package com.example.skillsproject.repositories;

import com.example.skillsproject.entities.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Page<Skill> findAll(Pageable pageable);
}
