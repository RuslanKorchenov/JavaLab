package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.dto.UserDto;
import ru.itis.services.interfaces.SignUpService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class ConfirmController {
    @Autowired
    private SignUpService signUpService;

    @GetMapping("/confirm/{code}")
    public String confirm(@PathVariable String code, HttpServletRequest request) {
        Optional<UserDto> userDto = signUpService.confirm(code);
        if (userDto.isPresent()) {
            return "redirect:/signIn";
        } else {
            return "signUp";
        }
    }
}
