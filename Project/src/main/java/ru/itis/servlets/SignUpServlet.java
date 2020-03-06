package ru.itis.servlets;

import freemarker.template.Configuration;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.itis.dto.SignUpDto;
import ru.itis.services.interfaces.SignUpService;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private Configuration freemarkerConfig;
    private SignUpService signUpService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        signUpService = springContext.getBean(SignUpService.class);
        freemarkerConfig = springContext.getBean(freemarker.template.Configuration.class);
        freemarkerConfig.setServletContextForTemplateLoading(context, "/WEB-INF/templates/");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write(FreeMarkerTemplateUtils
                .processTemplateIntoString(freemarkerConfig.getTemplate("signUp.ftl"), null));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail(request.getParameter("email"));
        signUpDto.setUsername("username");
        signUpDto.setPassword("password");
        signUpService.signUp(signUpDto);
        response.sendRedirect("/signUp");
    }
}
