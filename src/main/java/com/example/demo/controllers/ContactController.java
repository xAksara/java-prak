package com.example.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {
    @GetMapping(value = {"/contact"})
    public String getIndex() {
        return "contact";
    }
}