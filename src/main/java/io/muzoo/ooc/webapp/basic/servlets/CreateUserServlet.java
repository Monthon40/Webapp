package io.muzoo.ooc.webapp.basic.servlets;

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

public class CreateUserServlet extends HttpServlet implements Routable {


    private SecurityService securityService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(securityService.isAuthorized(request)){
            boolean authorized = securityService.isAuthorized(request);
            if(authorized){
//                String username = (String) request.getSession().getAttribute("username");
//                UserService userService = UserService.getInstance();

//                request.setAttribute("user",userService.findByUsername(username));

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(securityService.isAuthorized(request)){
            boolean authorized = securityService.isAuthorized(request);
            if(authorized){

                //ensure that username and displayName do not contain any spaces
                String username = StringUtils.trim((String) request.getParameter("username"));
                String displayName = StringUtils.trim((String) request.getParameter("display_name"));
                String password = (String) request.getParameter("password");
                String cpassword = (String) request.getParameter("cpassword");

                UserService userService = UserService.getInstance();
                String errorMessage = null;
                //check if username is valid
                if(userService.findByUsername(username) != null){
                    errorMessage = String.format("Username %s has already been taken", username);
                }

                //check if displayName is valid
                else if(StringUtils.isBlank(displayName)){
                    errorMessage ="DisplayName cannot be blank";
                }

                //check if confirm password is same as password
                else if(!StringUtils.equals(password,cpassword)){
                    errorMessage ="Password does not match";
//                    errorMessage = String.format("Username %s has already been taken", username);

                }

                if (errorMessage != null){
                    request.getSession().setAttribute("hasError",true);
                    request.getSession().setAttribute("message", errorMessage);
                } else{
                    //create new user
                    try{
                        userService.createUser(username,password,displayName);
                        request.getSession().setAttribute("hasError",false);
                        request.getSession().setAttribute("message", String.format("Username %s has been created successfully.", username));
                        response.sendRedirect("/");
                        return;

                    } catch (Exception e){
                        request.getSession().setAttribute("hasError",true);
                        request.getSession().setAttribute("message", e.getMessage());
                    }
                }
                request.setAttribute("username", username);
                request.setAttribute("display_name", displayName);
                request.setAttribute("password", password);
                request.setAttribute("cpassword", cpassword);

                //if not successful it will reach here
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
}
