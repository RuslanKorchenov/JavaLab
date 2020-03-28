package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ru.itis.dto.SignUpDto;
import ru.itis.services.interfaces.SignUpService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @SneakyThrows
    @PostMapping(value = "/signUp")
    public String signUp(SignUpDto signUpDto) {
        signUpService.signUp(signUpDto);
        return "signUp";
    }

}
