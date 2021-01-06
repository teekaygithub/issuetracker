package com.tomkato.issuetracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import com.tomkato.issuetracker.GetConnectionUtilities;

public class CreateTicketsServlet extends HttpServlet {
    
    private Logger log = LoggerFactory.getLogger(CreateTicketsServlet.class);
    private String message;
    
    @Override
    public void init() throws ServletException {
        message = "Your project tickets";
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Serving POST request");
        Connection conn = null;
        Statement stmt = null;
        String submit = request.getParameter("submit");
        log.debug("Submit type: {}", submit);
        if ((submit != null) && (submit.equals("PUT"))) {
            doPut(request, response);
            return;
        }
        
        String name = request.getParameter("name");
        String project = request.getParameter("project");
        String description = request.getParameter("description");
        log.info("name: {}, project: {}, description: {}", name, project, description);
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
                log.info("SQL command: {}", sql);
                out.println("<p>POST request complete!</p>");
            } else {
                out.println("<p>Request cannot be processed, please enter a name for the desired ticket.</p>");
                log.error("POST request failed");
            }
            out.println("<a href=\"index.html\">Back to home</a>");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("POST request complete.");
    }
    
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Serving PUT request");
        Connection conn = null;
        Statement stmt = null;
        // String name;
        // String project;
        // String description;
        String status = request.getParameter("status");
        String id = request.getParameter("id");
        if (id == null ){throw new IOException();}
        
        PrintWriter out = response.getWriter();
        
        try {
            response.setContentType("text/html");
            conn = GetConnectionUtilities.getConnection();
            stmt = conn.createStatement();
            
            // First check if the entry exists or not
            String sql = "SELECT COUNT(*) as total from issuetracker WHERE id="+id;
            ResultSet rs = stmt.executeQuery(sql);
            log.debug("SQL command: {}", sql);
            while(rs.next()) {
                if (rs.getInt("total") == 0) {
                    log.error("PUT request - unable to find entry with ID {}", id);
                    throw new IOException();
                }
            }
            
            // name = rs.getString("name");
            // project = rs.getString("project");
            // description = rs.getString("description");
            // status = rs.getString("status");
            
            // Check which field(s) we are updating
            // if ((request.getParameter("name") != null) && (name != request.getParameter("name"))) {name = request.getParameter("name");}
            // if ((request.getParameter("project") != null) && (project != request.getParameter("project"))) {project = request.getParameter("project");}
            // if ((request.getParameter("description") != null) && (description != request.getParameter("description"))) {description = request.getParameter("description");}
            // if ((request.getParameter("status") != null) && (status != request.getParameter("status"))) {status = request.getParameter("status");}

            sql = "UPDATE issuetracker SET status = \"" + status + 
            "\" WHERE id = " + id;
            boolean rc = stmt.execute(sql);
            log.debug("SQL command: {}", sql);
            out.println("<p>PUT request complete!</p>");
            
            out.println("<a href=\"/tickets\">Back to home</a>");
            
            rs.close();
            stmt.close();
            conn.close();
            log.debug("PUT request complete");
        } catch (Exception e) {
            log.error("PUT request failed");
            e.printStackTrace();
        }
    }
    
    @Override
    public void destroy() {
        // do nothing
    }
}