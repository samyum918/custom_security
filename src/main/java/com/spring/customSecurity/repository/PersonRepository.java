package com.spring.customSecurity.repository;

import com.spring.customSecurity.dto.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> getAllPersons();
    List<String> getAllPersonNames();
    Person getPersonNamesByUid(String userId);
}
