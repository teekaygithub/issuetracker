import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;

public class CreateTicketsServlet extends HttpServlet {
    //
    private String message;
    
    public void init() throws ServletException {
        message = "Your project tickets";
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        PrintWriter out = response.getWriter();
        
        try {
            response.setContentType("text/html");
            out.println("<!DOCTYPE html>");
            conn = CreateTicketsServlet.getConnection();
            stmt = conn.createStatement();
            if (name != null) {
                String sql;
                sql = "INSERT INTO issuetracker VALUE(\""+name+"\", \"open\", \"0\", \""+description+"\")";
                boolean rs = stmt.execute(sql);
                out.println("<p>POST request complete!</p>");
            } else {
                out.println("<p>Request cannot be processed, please enter a name for the desired ticket.</p>");
            }
            
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("POST request complete.");
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
    
    public void destroy() {
        // do nothing
    }
}