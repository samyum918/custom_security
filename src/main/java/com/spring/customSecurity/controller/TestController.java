package com.spring.customSecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
//    @PreAuthorize("hasAuthority('MAKER')")
    @GetMapping("test1")
    public String test1() {
        return "test1";
    }

    @PreAuthorize("hasAuthority('CHECKER')")
    @GetMapping("test2")
    public String test2() {
        return "test2";
    }
}
