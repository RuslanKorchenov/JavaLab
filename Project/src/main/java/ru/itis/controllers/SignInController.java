package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignInDto;
import ru.itis.services.interfaces.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class SignInController {
    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView getSignPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signIn");
        return modelAndView;
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ModelAndView signIn(SignInDto signInDto, HttpServletResponse response) {
        Optional<String> cookieCandidate = signInService.signIn(signInDto);
        ModelAndView modelAndView = new ModelAndView();
        if (cookieCandidate.isPresent()) {
            Cookie cookie = new Cookie("AUTH", cookieCandidate.get());
            response.addCookie(cookie);
            modelAndView.setViewName("file_upload");
        } else {
            //TODO: send some error to signIn.ftl
            modelAndView.setViewName("signIn");
        }
        return modelAndView;
    }
}