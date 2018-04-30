package com.alex.qasystem.interceptor;

import com.alex.qasystem.dao.AuthTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptior implements HandlerInterceptor {

    @Autowired
    private AuthTokenMapper authTokenMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("token") == null) {
            response.sendRedirect("toLogin");
            return false;
        }
        return true;
    }

}
