package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.service.SecurityService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet implements Routable{

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
        requestDispatcher.include(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String error = "";
//        if(securityService.login(request)){
//            response.sendRedirect("/");
//        } else{
//            error = "User name or password invalid";
//            request.setAttribute("error", error);
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
//            requestDispatcher.include(request,response);
//
//        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(!StringUtils.isBlank(username) && ! StringUtils.isBlank(password)){
            if(securityService.authenticate(username, password, request)){
                response.sendRedirect("/");
            } else{
                String error = "Wrong username or password";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                rd.include(request, response);
        }

        } else{
            String error = "Username or password is missing";
            request.setAttribute("error", error);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            requestDispatcher.include(request,response);
        }
    }

    @Override
    public String getPattern() {
        return "/login";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}