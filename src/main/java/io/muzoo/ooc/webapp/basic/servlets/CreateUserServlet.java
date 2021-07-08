package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.service.SecurityService;
import io.muzoo.ooc.webapp.basic.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CreateUserServlet extends HttpServlet implements Routable {


    private SecurityService securityService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(securityService.isAuthorized(request)){
            boolean authorized = securityService.isAuthorized(request);
            if(authorized){
                String username = (String) request.getSession().getAttribute("username");
                UserService userService = UserService.getInstance();


                request.setAttribute("user",userService.findByUsername(username));

                Date date = new Date();
                request.setAttribute("date1", date);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/create.jsp");
                rd.include(request,response);

                //doesn't look like session attributes were removed
                request.getSession().removeAttribute("hasError");
                request.getSession().removeAttribute("message");
            }

        } else{
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }

    }

    @Override
    public String getPattern() {
        return "/user/create";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
