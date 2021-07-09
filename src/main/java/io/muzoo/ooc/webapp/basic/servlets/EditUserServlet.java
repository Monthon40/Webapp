package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.Routable;
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

public class EditUserServlet extends HttpServlet implements Routable {


    private SecurityService securityService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(securityService.isAuthorized(request)){
            boolean authorized = securityService.isAuthorized(request);
            if(authorized){
                String username = StringUtils.trim((String) request.getParameter("username"));
                UserService userService = UserService.getInstance();

                User user = userService.findByUsername(username);
                request.setAttribute("user",user);
                request.setAttribute("username", user.getUsername());
                request.setAttribute("display_name", user.getDisplay_name());

                Date date = new Date();
                request.setAttribute("date1", date);

                //if not successful it will reach here
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/edit.jsp");
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
        return "/user/edit";
    }
    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(securityService.isAuthorized(request)){
            boolean authorized = securityService.isAuthorized(request);
            if(authorized){

                //ensure that username and displayName do not contain any spaces
                String username = StringUtils.trim((String) request.getParameter("username"));
                String displayName = StringUtils.trim((String) request.getParameter("display_name"));

                UserService userService = UserService.getInstance();
                User user = userService.findByUsername(username);

                String errorMessage = null;
                //check if username exists
                if(user == null){
                    errorMessage = String.format("User %s does not exists", username);
                }

                //check if displayName is valid
                else if(StringUtils.isBlank(displayName)){
                    errorMessage ="DisplayName cannot be blank";
                }


                if (errorMessage != null){
                    request.getSession().setAttribute("hasError",true);
                    request.getSession().setAttribute("message", errorMessage);
                } else{
                    //edit user
                    try{
                        userService.updateUserByUsername(username,displayName);
                        request.getSession().setAttribute("hasError",false);
                        request.getSession().setAttribute("message", String.format("Username %s has been updated successfully.", username));
                        response.sendRedirect("/");
                        return;

                    } catch (Exception e){
                        request.getSession().setAttribute("hasError",true);
                        request.getSession().setAttribute("message", e.getMessage());
                    }
                }
                request.setAttribute("username", username);
                request.setAttribute("display_name", displayName);

                //if not successful it will reach here
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/edit.jsp");
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
}
