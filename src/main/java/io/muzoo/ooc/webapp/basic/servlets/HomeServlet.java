//package io.muzoo.ooc.webapp.basic.servlets;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.Date;
//
//public class HomeServlet extends AbstractRoutableHttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        if(securityService.isAuthorized(request)){
//            String username = securityService.getCurrentUsername(request);
//            request.setAttribute("username",username);
//
//            Date date = new Date();
//            request.setAttribute("date1", date);
//
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/home.jsp");
//            requestDispatcher.include(request,response);
//        } else{
//            response.sendRedirect("/login");
//        }
//
//    }
//
//    @Override
//    public String getPattern() {
//        return "/index.jsp";
//    }
//}
