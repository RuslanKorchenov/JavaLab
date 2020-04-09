package ru.itis.controllers.rest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.SignUpDto;
import ru.itis.services.interfaces.SignUpService;

@RestController
public class RestSignUpController {

    @Autowired
    public SignUpService signUpService;

    @SneakyThrows
    @PostMapping("/rest/signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
        if (signUpService.signUp(signUpDto)) {
            return ResponseEntity.status(HttpStatus.OK).body("You have successfully registered." +
                    "A confirmation email has arrived");
        }
        return ResponseEntity.badRequest().body("A user with this email exists");
    }
}
