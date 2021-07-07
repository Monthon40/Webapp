package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class  LogoutServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        securityService.logout(request);
        response.sendRedirect("/");
    }

    @Override
    public String getPattern() {
        return "/logout";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;

    }
}