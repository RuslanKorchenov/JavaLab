package ru.itis.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.filter.GenericFilterBean;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(0)
    @AllArgsConstructor
    public static class ApiConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationProvider authenticationProvider;

        @Autowired
        @Qualifier(value = "jwtAuthenticationFilter")
        private GenericFilterBean jwtAuthenticationFilter;

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/rest/signIn");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/rest/**");

            http.formLogin().disable();
            http.logout().disable();

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
        }

        @Autowired
        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(authenticationProvider);
        }
    }

    @Configuration
    @Order(1)
    @AllArgsConstructor
    public static class GeneralConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier(value = "myUserDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private PersistentTokenRepository persistentTokenRepository;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/signIn", "/signUp", "/confirm", "/")
                    .permitAll()
                    .antMatchers("/files", "/products/**")
                    .authenticated();

            http.formLogin().loginPage("/signIn")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/files")
                    .failureUrl("/signIn")
                    .permitAll();

            http.rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository);

            http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/signIn")
                    .deleteCookies("JSESSIONID", "remember-me")
                    .invalidateHttpSession(true);
        }
    }
}
