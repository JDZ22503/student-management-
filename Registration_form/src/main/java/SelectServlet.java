import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/EmployeeServlet")
public class SelectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<!DOCTYPE html>\r\n"
                + "<html lang=\"en\">\r\n"
                + "<head>\r\n"
                + "    <meta charset=\"UTF-8\">\r\n"
                + "    <meta name=\"viewport\" content=\"width=, initial-scale=1.0\">\r\n"
                + "    <title>Document</title>\r\n"
                + "    <style>\r\n"
                + "        * {\r\n"
                + "            margin: 0;\r\n"
                + "            padding: 0;\r\n"
                + "        }\r\n"
                + "        body {\r\n"
                + "            width: 100%;\r\n"
                + "            height: 100vh;\r\n"
                      
                + "            justify-content: center;\r\n"
                + "            align-items: center;\r\n"
                        
                + "        }\r\n"
                + "        #navbar {\r\n"
                + "            display: flex;\r\n"
                + "            align-items: center;\r\n"
                + "            position: relative;\r\n"
                + "            justify-content: center;\r\n"
                + "            background-color: #088178;\r\n"
                + "            height: 50px;\r\n"
                + "        }\r\n"
                + "        #navbar li {\r\n"
                + "            display: flex;\r\n"
                + "            list-style: none;\r\n"
                + "            padding: 0 40px;\r\n"
                + "            position: relative;\r\n"
                + "            flex-direction: row;\r\n"
                + "        }\r\n"
                + "        #navbar li a {\r\n"
                + "            display: flex;\r\n"
                + "            text-decoration: none;\r\n"
                + "            font-size: 20px;\r\n"
                + "            font-weight: 600;\r\n"
                + "            color: #ffffff;\r\n"
                + "            align-items: center;\r\n"
                + "            flex-direction: row;\r\n"
                + "            transition: 0.3s ease;\r\n"
                + "        }\r\n"
                + "        #navbar li a:hover,\r\n"
                + "        #navbar li a.active {\r\n"
                + "            color: #8af3e3;\r\n"
                + "        }\r\n"
                + "        #navbar li a.active::after,\r\n"
                + "        #navbar li a:hover::after {\r\n"
                + "            content: \"\";\r\n"
                + "            width: 30%;\r\n"
                + "            height: 2px;\r\n"
                + "            background: #f6fdfc;\r\n"
                + "            position: absolute;\r\n"
                + "            bottom: -4px;\r\n"
                + "            left: 40px;\r\n"
                + "        }\r\n"
                + "        .insert {\r\n"
                + "            width: 100%;\r\n"
                + "            height: 50%;\r\n"
                + "            display: flex;\r\n"
                + "            flex-direction:row;\r\n"
                + "            justify-content: center;\r\n"
                + "            padding: 0;\r\n"
                + "        }\r\n"
                + "        .insert h3 {\r\n"
                + "            text-align: center;\r\n"
                + "        }\r\n"
                + "        table {\r\n"
                + "            border: 1px solid #000;\r\n"
                + "             border-collapse: collapse;\r\n"
                + "            width: 97%;\r\n"
                + "            text-align: center;\r\n"
                + "            margin: 20px;\r\n"
                + "        }\r\n"
                + "        th, td {\r\n"
                + "            padding: 10px;\r\n"
                + "            border: 1px solid #000;\r\n"
                + "            border-collapse: collapse;\r\n"
                + "        }\r\n"
                + "        th{background-color:#088178;color:white;}\r\n"
                + "    </style>\r\n"
                + "</head>\r\n"
                + "<body>\r\n"
                + "    <div class=\"navbar\">\r\n"
                + "        <ul id=\"navbar\">\r\n"
                + "            <li><a href=\"index.html\">Insert</a></li>\r\n"
                + "            <li><a href=\"update.html\">Update</a></li>\r\n"
                + "            <li><a href=\"delete.html\">Delete</a></li>\r\n"
                + "            <li><a href=\"SelectServlet\">Show table</a></li>\r\n"
                + "        </ul>\r\n"
                + "    </div>\r\n"
                + "</body>\r\n"
                + "</html>");

        out.println("<html><head><title>Employee Records</title></head><body>");

        try {
            // Execute select query
            ResultSet resultSet = selectQuery();

            // Display data in HTML table
            out.println("<table border='1'><tr><th>Emp ID</th><th>Emp Name</th><th>Join Date</th><th>Salary</th><th>Designation</th><th>Age</th></tr>");
            while (resultSet.next()) {
                int emp_id = resultSet.getInt("emp_id");
                String emp_name = resultSet.getString("emp_name");
                String join_date = resultSet.getString("join_date");
                int salary = resultSet.getInt("salary");
                String designation = resultSet.getString("designation");
                int age = resultSet.getInt("age");

                out.println("<tr><td>" + emp_id + "</td><td>" + emp_name + "</td><td>" + join_date +
                        "</td><td>" + salary + "</td><td>" + designation + "</td><td>" + age);
            }
            out.println("</table>");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage()); // Send a more informative message to the client
        }
        out.println("</body></html>");
    }  public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "jenish@12345");
    }
    // Method to execute select query
    public static ResultSet selectQuery() throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        PreparedStatement prestmt = con.prepareStatement("select * from employee");
        return prestmt.executeQuery();
    }
  
}
