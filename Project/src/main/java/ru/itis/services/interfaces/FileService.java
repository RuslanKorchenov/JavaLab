package ru.itis.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UserDto;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    String saveFile(MultipartFile file, UserDto userDto);
    void writeFileToResponse(String fileName, HttpServletResponse response);
}
