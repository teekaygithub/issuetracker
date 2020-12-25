package com.tomkato.issuetracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;

// import com.tomkato.issuetracker.GetConnectionUtilities;

public class ReadTicketsServlet extends HttpServlet {
    //
    private String message;
    
    @Override
    public void init() throws ServletException {
        message = "Your project tickets";
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\" />");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>"+message+"</h1>");
        
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = GetConnectionUtilities.getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM issuetracker";
            ResultSet rs = stmt.executeQuery(sql);
            
            out.println("<table border='2'><th>ID</th><th>Project</th><th>Ticket Name</th><th>Description</th><th>Status</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>"+rs.getInt("id")+"</td>");
                out.println("<td>"+rs.getString("project")+"</td>");
                out.println("<td>"+rs.getString("name")+"</td>");
                out.println("<td>"+rs.getString("description")+"</td>");
                out.println("<td>"+rs.getString("status")+"</td>");
                out.println("</tr>");
            }
            out.println("</table><br>");
            out.println("<a href=\"index.html\">Back to Home</a>");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("</body>");
        System.out.println("GET request complete.");
    }
    
    @Override
    public void destroy() {
        // do nothing
    }
}