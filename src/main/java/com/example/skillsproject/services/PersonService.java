package com.example.skillsproject.services;

import com.example.skillsproject.dto.PersonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface PersonService {

    Page<PersonDto> getAllPeople(Pageable pageable);

    public PersonDto getPerson(Long id);

    @Transactional
    PersonDto createPerson(PersonDto personDto);

    @Transactional
    PersonDto updatePerson(Long id, PersonDto personDto);

    public void deletePerson(Long id);
}
