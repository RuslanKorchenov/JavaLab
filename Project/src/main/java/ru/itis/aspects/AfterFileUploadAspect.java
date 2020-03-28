package ru.itis.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.models.FileInfo;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.interfaces.EmailService;


import java.util.Optional;

@Component
@Aspect
public class AfterFileUploadAspect {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UsersRepository usersRepository;

    private final String link = "localhost:8080/files/";

    @AfterReturning(pointcut = "execution(* ru.itis.services.impl.FileServiceImpl.saveFile(..))", returning= "file")
    public void uploadAfter(FileInfo file){
        Optional<User> user = usersRepository.find(file.getUserId());
        emailService.sendMail("Successful add photo!",
                "You can open this on this link: " + link + file.getStorageFileName(),
                user.get().getEmail());
    }
}
