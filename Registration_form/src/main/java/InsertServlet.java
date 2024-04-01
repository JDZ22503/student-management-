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

import java.sql.SQLException;


@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int result = 0;
        PrintWriter out = response.getWriter();

        try {
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            String emp_Name = request.getParameter("emp_name");
            String join_date = request.getParameter("join_date");
            int salary = Integer.parseInt(request.getParameter("salary"));
            String designation = request.getParameter("designation");
            int age = Integer.parseInt(request.getParameter("age"));

            result = insertQuery(emp_id, emp_Name, join_date, salary, designation, age);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage()); // Send a more informative message to the client
            return; // Stop execution if an error occurs
        }

        response.setContentType("text/html");

        if (result == 1) {
            out.println("<html><head><script>alert('Data added successfully');"
                    + "window.location.href='index.html';</script></head></html>");
        } else {
            out.println("<html><head><script>alert('Data not inserted');</script></head></html>");
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "jenish@12345");
    }

    public static int insertQuery(int emp_id, String emp_name, String join_date, int salary, String designation,
                                  int age)
            throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement prestmt = con.prepareStatement("insert into employee values(?,?,?,?,?,?)")) {

            prestmt.setInt(1, emp_id);
            prestmt.setString(2, emp_name);
            prestmt.setString(3, join_date);
            prestmt.setInt(4, salary);
            prestmt.setString(5, designation);
            prestmt.setInt(6, age);

            int result = prestmt.executeUpdate();
            return result;
        }
    }
}
