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
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int result = 0;
        PrintWriter out = response.getWriter();

        try {
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            String emp_name = request.getParameter("emp_name");
            String join_date = request.getParameter("join_date");
            int salary = Integer.parseInt(request.getParameter("salary"));
            String designation = request.getParameter("designation");
            int age = Integer.parseInt(request.getParameter("age"));

            // Update
            result = updateQuery(emp_id, emp_name, join_date, salary, designation, age);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage()); // Send a more informative message to the client
            return; // Stop execution if an error occurs
        }

        if (result == 1) {
            out.println("<html><head><script>alert('Data updated successfully');"
                    + "window.location.href='SelectServlet';</script></head></html>");
        } else {
            out.println("Data Not Updated");
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "jenish@12345");
    }

    public static int updateQuery(int emp_id, String emp_name, String join_date, int salary, String designation,
                                  int age)
            throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement prestmt = con.prepareStatement(
                     "update employee set emp_name=?, join_date=?, salary=?, designation=?, age=? where emp_id=?")) {

            prestmt.setString(1, emp_name);
            prestmt.setString(2, join_date);
            prestmt.setInt(3, salary);
            prestmt.setString(4, designation);
            prestmt.setInt(5, age);
            prestmt.setInt(6, emp_id);

            return prestmt.executeUpdate();
        }
    }
}
