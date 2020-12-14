import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;

public class ReadTicketsServlet extends HttpServlet {
    //
    private String message;
    
    public void init() throws ServletException {
        message = "Your project tickets";
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        out.println("<h1>"+message+"</h1>");
        
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = ReadTicketsServlet.getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM issuetracker";
            ResultSet rs = stmt.executeQuery(sql);
            
            out.println("<table border='2'></th><th>Ticket Name</th><th>Status</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>"+rs.getString("name")+"</td>");
                out.println("<td>"+rs.getString("status")+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("GET request complete.");
    }
    
    public static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
        
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
        
        return DriverManager.getConnection(dbUrl, username, password);
    }
    
    public void destroy() {
        // do nothing
    }
}