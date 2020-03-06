package ru.itis.services.interfaces;

import ru.itis.dto.SignUpDto;

public interface SignUpService {
    void signUp(SignUpDto form);
    boolean confirm(String code);

}
