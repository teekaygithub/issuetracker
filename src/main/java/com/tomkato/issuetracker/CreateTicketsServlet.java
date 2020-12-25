package com.tomkato.issuetracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;

// import com.tomkato.issuetracker.GetConnectionUtilities;

public class CreateTicketsServlet extends HttpServlet {
    //
    private String message;
    
    @Override
    public void init() throws ServletException {
        message = "Your project tickets";
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        String name = request.getParameter("name");
        String project = request.getParameter("project");
        String description = request.getParameter("description");
        PrintWriter out = response.getWriter();
        
        try {
            response.setContentType("text/html");
            out.println("<!DOCTYPE html>");
            conn = GetConnectionUtilities.getConnection();
            stmt = conn.createStatement();
            if (name != null) {
                String sql;
                sql = "INSERT INTO issuetracker " + 
                "(name, project, status, description) " +
                "VALUES(\"" +
                name +
                "\", \"" +
                project +
                "\", \"open\", \"" + 
                description + 
                "\")";
                boolean rs = stmt.execute(sql);
                out.println("<p>POST request complete!</p>");
            } else {
                out.println("<p>Request cannot be processed, please enter a name for the desired ticket.</p>");
            }
            out.println("<a href=\"index.html\">Back to home</a>");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("POST request complete.");
    }
    
    @Override
    public void destroy() {
        // do nothing
    }
}