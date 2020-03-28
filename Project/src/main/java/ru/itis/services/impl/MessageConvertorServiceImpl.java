package ru.itis.services.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.itis.services.interfaces.MessageConvertorService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageConvertorServiceImpl implements MessageConvertorService {

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public String fromEmailToFtl(String confirmCode) {
        Map<String, String> map = new HashMap<>();
        String link = "http://localhost:8080/confirm/" + confirmCode;
        map.put("code", link);
        Template t;
        String html;
        try {
            t = freemarkerConfig.getTemplate("email.ftl");
            html = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }

        return html;
    }
}
