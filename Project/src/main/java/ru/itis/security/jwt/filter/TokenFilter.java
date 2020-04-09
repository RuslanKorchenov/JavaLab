package ru.itis.security.jwt.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.security.jwt.authentication.JwtAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component("tokenFilter")
public class TokenFilter extends GenericFilterBean {

    @Autowired
    JwtHelper jwtHelper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            String token = jwtHelper.resolveToken((HttpServletRequest) servletRequest);
            if (token != null) {
                if (jwtHelper.validateToken(token)) {
                    Authentication authentication = new JwtAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (ServletException | IOException e) {
            throw new IllegalArgumentException(e);
        }

    }
}