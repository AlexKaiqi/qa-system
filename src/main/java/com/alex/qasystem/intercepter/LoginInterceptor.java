package com.alex.qasystem.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alex
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final String USER_SESSION_KEY = "user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (null == request.getSession().getAttribute(USER_SESSION_KEY)) {
            response.sendRedirect("toLogin");
            return false;
        }
        return true;
    }
}
