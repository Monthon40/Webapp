package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.security.SecurityService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractRoutableHttpServlet extends HttpServlet implements Routable {

    protected SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
