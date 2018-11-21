package com.snokant.projekt.Configuration.JwtConfiguration;

import com.snokant.projekt.Controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    UserController userController;
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //if (httpServletRequest.getRequestURI().equals("/rest/user/signin")) {
           // BufferedReader bufferedReader = httpServletRequest.getReader();
           // System.out.println(bufferedReader.read());
            //userController.index()
      //  } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
       // }
    }

}
