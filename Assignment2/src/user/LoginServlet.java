package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class login
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Get Served at: ").append(request.getContextPath());
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
//		response.getWriter().append("Post Served at: ").append(request.getContextPath());
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
		String action = request.getParameter("action");

        if (action != null) {
            // call different action depends on the action parameter
            if (action.equalsIgnoreCase("login")) {
                this.loginUser(request, response);
            }
        }else{
        	response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                out.println("<html>");
                out.println("<head>");
                out.println("  <title>Login Page</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Login Page</h1>");
                out.println("<form action='login' method='POST'>");
                out.println("<input name='action' type='hidden' value='login' />");
                out.println("  <p>Email: <input type='text' name='email' /></p>");
                out.println("  <p>Password: <input type='password' name='password' /></p>");
                out.println("  <p><input type='submit' value='Login' /></p>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            } finally { 
                out.close();
            }
        }
    }
	
    private void loginUser(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Customer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Customer (Retrieve)</h1>");
            out.println("<div style='width:600px'>");
            out.println("<fieldset>");
            out.println("<legend>Customer List</legend>");
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            if (email != null && !email.equalsIgnoreCase("") && 
            		password != null && !password.equalsIgnoreCase("")) {
	            // make connection to db and retrieve data from the table
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
	            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [customer] WHERE [email] = ? AND [password] = ?",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            pstmt.setString(1, email);
	            pstmt.setString(2, password);
	            
	            ResultSet rs = pstmt.executeQuery();
	            		
	            // total number of records
	            int numRow = 0;
	            if (rs != null && rs.last() != false) {
	                numRow = rs.getRow();
	                rs.beforeFirst();
	            }
	            
	            out.println("<p>Total "+numRow+" entries.</p>");
	            out.println("<div><table style='width:100%'>");
	            out.println("<thead>");
	            out.println("<th align='left'>Customer ID</th><th align='left'>First Name</th><th align='left'>Last Name</th><th align='left'>Email</th>");
	            out.println("</thead>");
	            out.println("<tbody>");
           
	            // list of data
	            while(rs != null && rs.next() != false){
	            	String customer_id = rs.getString("customer_id");
	                String first_name = rs.getString("first_name");
	                String last_name = rs.getString("last_name");
	                
	            	out.println("<tr>");
	                out.println("<td>" + this.htmlEncode(customer_id) + "</td>");
	                out.println("<td>" + this.htmlEncode(first_name) + "</td>");
	                out.println("<td>" + this.htmlEncode(last_name) + "</td>");
	                out.println("<td>" + email + "</td>");
	                out.println("</tr>");
	            }

	            out.println("</tbody>");
	            out.println("</table></div>");
	            out.println("</fieldset>");
	            out.println("</div>");
	            out.println("</body>");
	            out.println("</html>");
            
	            if (rs != null) {
	                rs.close();
	            }
	            if (con != null) {
	                con.close();
	            }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
    
    private String htmlEncode(String s) {

        StringBuffer sb = new StringBuffer(s.length() * 2);

        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if ((ch >= '?' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch == ' ') || (ch == '\n')) {
                sb.append(ch);
            }
            else {
                switch(ch) {
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '\'':
                        sb.append("&#039;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    default:
                        sb.append("&#");
                        sb.append(new Integer(ch).toString());
                        sb.append(';');
                }
            }
        }

        return sb.toString();
    }
}
