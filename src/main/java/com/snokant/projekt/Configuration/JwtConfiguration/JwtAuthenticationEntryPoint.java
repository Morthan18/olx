package com.snokant.projekt.Configuration.JwtConfiguration;

import com.snokant.projekt.Controller.UserController;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;

@Component
@ControllerAdvice
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    UserController userController;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        response.setContentType("image/gif");
        String filepath = "C:/Users/Krzys/Desktop/snokant/gif.gif";
        OutputStream out = response.getOutputStream();
        InputStream in = new FileInputStream(new File(filepath));
        try {
            copy(in, out);
        }finally{
            in.close();
        }
        out.close();
    }
    public void copy(final InputStream in, final OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int count;

        while ((count = in.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        out.flush();
    }

}
