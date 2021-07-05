package com.spring.customSecurity.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if(ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        log.info("IP: {}, URI: {}, Server Host: {}, Server Port:{}, Response Status: {}",
                ipAddress, request.getRequestURI(), request.getLocalAddr(), request.getServerPort(), response.getStatus());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
