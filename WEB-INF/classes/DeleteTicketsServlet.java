import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;

public class DeleteTicketsServlet extends HttpServlet {
    //
    private String message;
    
    @Override
    public void init() throws ServletException {
        message = "Your project tickets";
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doDelete(request, response);
    }
    
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        String id = request.getParameter("id");
        PrintWriter out = response.getWriter();
        
        try {
            response.setContentType("text/html");
            out.println("<!DOCTYPE html>");
            conn = CreateTicketsServlet.getConnection();
            stmt = conn.createStatement();
            if (id != null) {
                String sql;
                sql = "DELETE FROM issuetracker WHERE id = \""+id+"\"";
                boolean rs = stmt.execute(sql);
                out.println("<p>DELETE request complete!</p>");
            } else {
                out.println("<p>Request cannot be processed, please enter the ID for the ticket you wish to remove.</p>");
            }
            out.println("<a href=\"index.html\">Back to home</a>");
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("<p>SQL exception!</p>");
        } catch (URISyntaxException use) {
            use.printStackTrace();
            out.println("<p>URI exception!</p>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Exception caught!</p>");
        }
        System.out.println("DELETE request complete.");
    }
    
    public static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String envdburl = System.getenv("CLEARDB_DATABASE_URL");
        if (envdburl == null) {throw new URISyntaxException(envdburl, "null");}
        URI dbUri = new URI(envdburl);
        
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
        
        return DriverManager.getConnection(dbUrl, username, password);
    }
 
    @Override
    public void destroy() {
        // do nothing
    }
}