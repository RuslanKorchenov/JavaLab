package ru.itis.services.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.itis.services.interfaces.EmailService;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Value("testinfo303@gmail.com")
    private String userName;

    @Override
    public void sendMail(String subject, String text, String email) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            Map<String, String> map = new HashMap<>();
            String link = "http://localhost:8080/confirm?code=" + text;
            map.put("code", link);
            Template t = freemarkerConfig.getTemplate("email.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
            messageHelper.setFrom(userName);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
        };
        emailSender.send(messagePreparator);
    }
}