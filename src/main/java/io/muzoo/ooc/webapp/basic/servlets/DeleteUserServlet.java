package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.model.User;
import io.muzoo.ooc.webapp.basic.service.SecurityService;
import io.muzoo.ooc.webapp.basic.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class DeleteUserServlet extends HttpServlet implements Routable {


    private SecurityService securityService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(securityService.isAuthorized(request)){
            boolean authorized = securityService.isAuthorized(request);
            if(authorized){
                String username = (String) request.getSession().getAttribute("username");
                UserService userService = UserService.getInstance();

                try{
                    User currentUser = userService.findByUsername(username);
                    User deletingUser = userService.findByUsername(request.getParameter("username"));
                    if(StringUtils.equals(currentUser.getUsername(), deletingUser.getUsername())){
                        request.getSession().setAttribute("hasError",true);
                        request.getSession().setAttribute("message", String.format("You cannot delete your own account %s.", deletingUser.getUsername()));
                    } else{
                        if(userService.deleteUserByUsername(deletingUser.getUsername())){
                            request.getSession().setAttribute("hasError",false);
                            request.getSession().setAttribute("message", String.format("User %s is successfully deleted.", deletingUser.getUsername()));
                        } else{
                            request.getSession().setAttribute("hasError",true);
                            request.getSession().setAttribute("message", String.format("Unable to delete user %s.", deletingUser.getUsername()));
                        }
                    }
                } catch (Exception e){
                    request.getSession().setAttribute("hasError",true);
                    request.getSession().setAttribute("message", String.format("Unable to delete user %s.", request.getParameter("username")));
                }
            }
            response.sendRedirect("/");
        } else{
            response.sendRedirect("/login");
        }

    }

    @Override
    public String getPattern() {
        return "/user/delete";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}



