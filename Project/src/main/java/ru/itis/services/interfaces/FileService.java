package ru.itis.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UserDto;
import ru.itis.models.FileInfo;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    FileInfo saveFile(MultipartFile file, Long id);
    void writeFileToResponse(String fileName, HttpServletResponse response);
}
