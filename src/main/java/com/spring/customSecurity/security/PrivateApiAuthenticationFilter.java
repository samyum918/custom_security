package com.spring.customSecurity.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.customSecurity.dto.Person;
import com.spring.customSecurity.model.User;
import com.spring.customSecurity.repository.PersonRepository;
import com.spring.customSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class PrivateApiAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private PersonRepository personRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
//        SecurityContextHolder.clearContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            if(request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
                //no session
                response.sendRedirect(request.getContextPath() + "/login");
            }
            else if(request.getSession(false) == null) {
                //session expired
                response.sendRedirect(request.getContextPath() + "/login");
            }
            else {
                Object obj = request.getSession(false).getAttribute("person");
                if(obj != null) {
                    if (obj instanceof Person) {
                        Person person = (Person) obj;
                        UserDetails user = new User(person.getUserId(), passwordEncoder.encode("secret"), true,
                                true, true, true, new ArrayList<>());
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        chain.doFilter(request, response);
                    }
                    else {
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                }
                else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }


//            UserDetails user = new User("abc", passwordEncoder.encode("secret"), true,
//                    true, true, true, new ArrayList<>());
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    @Bean
    public FilterRegistrationBean<PrivateApiAuthenticationFilter> filter()
    {
        FilterRegistrationBean<PrivateApiAuthenticationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new PrivateApiAuthenticationFilter());
        bean.addUrlPatterns("/api/**");
        return bean;
    }
}
