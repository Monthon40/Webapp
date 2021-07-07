package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.service.SecurityService;

public interface Routable {

    String getPattern();

    void setSecurityService(SecurityService securityService);
}
