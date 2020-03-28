package ru.itis.services.interfaces;

import freemarker.template.TemplateException;
import ru.itis.dto.SignUpDto;
import ru.itis.dto.UserDto;

import java.io.IOException;
import java.util.Optional;

public interface SignUpService {
    void signUp(SignUpDto form) throws IOException, TemplateException;
    Optional<UserDto> confirm(String code);

}
