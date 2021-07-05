package com.spring.customSecurity.controller;

import com.spring.customSecurity.dto.Person;
import com.spring.customSecurity.repository.PersonRepository;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class LdapController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/get-user-names")
    public List<String> getLdapUserNames() {
        return personRepository.getAllPersonNames();
    }

    @GetMapping("/get-users")
    public List<Person> getLdapUsers() {
        return personRepository.getAllPersons();
    }

    @GetMapping("/get-user")
    public Person findLdapPerson(@RequestParam(name = "user-id") String userId) {
        return personRepository.getPersonNamesByUid(userId);
    }
}
