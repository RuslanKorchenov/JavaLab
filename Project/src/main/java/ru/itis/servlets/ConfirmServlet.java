package ru.itis.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.services.interfaces.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {

    private SignUpService signUpService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        signUpService = springContext.getBean(SignUpService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        signUpService.confirm(request.getParameter("code"));
        response.sendRedirect("/signUp");
    }
}
