package com.spring.customSecurity.controller;

import com.spring.customSecurity.dto.Person;
import com.spring.customSecurity.repository.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/public")
@Tag(name = "PublicController")
public class PublicController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("test")
    @Operation(tags = "PublicController", operationId = "test", summary = "")
    public String test() {
        return "test";
    }

    @GetMapping("/get-user")
    public Person findLdapPerson(@RequestParam(name = "user-id") String userId, HttpSession httpSession) {
        if(httpSession.getAttribute("person") != null) {
            log.info("Session has person object, person: " + httpSession.getAttribute("person").toString());
            return (Person) httpSession.getAttribute("person");
        }
        else {
            Person person = personRepository.getPersonNamesByUid(userId);
            log.info("Session doesnt have person object.");
            if(person != null) {
                httpSession.setAttribute("person", person);
                return person;
            }
            else {
                return null;
            }
        }
    }

    @GetMapping("/invalidate")
    public void invalidate(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
