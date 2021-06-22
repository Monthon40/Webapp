package io.muzoo.ooc.webapp.basic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
        requestDispatcher.include(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String error = "";

        // authentication
        if (username != null && username.equals("strickwar") && password != null && password.equals("12345")){
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect("/");
        } else{
            error = "Username or password invalid. Please try again!";

            request.setAttribute("error", error);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            requestDispatcher.include(request,response);
        }
    }
}