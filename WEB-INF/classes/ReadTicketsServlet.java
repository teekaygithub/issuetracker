import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;

public class ReadTicketsServlet extends HttpServlet {
    //
    private String message;
    
    public void init() throws ServletException {
        message = "Hello World";
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        out.println("<h1>"+message+"</h1>");
        
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ReadTicketsServlet.getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM project";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                out.println("<p>"+rs.getString("name")+"</p>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }
        out.println("<p>wat</p>");
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