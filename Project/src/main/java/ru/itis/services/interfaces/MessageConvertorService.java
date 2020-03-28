package ru.itis.services.interfaces;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface MessageConvertorService {
    String fromEmailToFtl(String email) throws IOException, TemplateException;
}
