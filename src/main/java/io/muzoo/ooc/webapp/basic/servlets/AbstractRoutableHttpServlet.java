package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.Routable;
import io.muzoo.ooc.webapp.basic.service.SecurityService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractRoutableHttpServlet extends HttpServlet implements Routable {

    protected SecurityService securityService;

}
