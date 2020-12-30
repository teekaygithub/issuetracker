package com.tomkato.issuetracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

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
        List<Ticket> tickets = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = GetConnectionUtilities.getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM issuetracker";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(Integer.toString(rs.getInt("id")));
                ticket.setName(rs.getString("name"));
                ticket.setProject(rs.getString("project"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                tickets.add(ticket);
            }
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("tickettable.jsp").forward(request, response);
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