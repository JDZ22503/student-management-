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

public class deleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int result = 0;
        PrintWriter out = response.getWriter();

        try {
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
          

            // Update
            result = deleteQuery(emp_id);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage()); // Send a more informative message to the client
            return; // Stop execution if an error occurs
        }

        if (result == 1) {
            out.println("<html><head><script>alert('Data deleted successfully');"
                    + "window.location.href='SelectServlet';</script></head></html>");
        } else {
            out.println("Data Not Updated");
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "jenish@12345");
    }

    public static int deleteQuery(int emp_id) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement prepstmt = con.prepareStatement("delete from employee where emp_id = ?")) {

            prepstmt.setInt(1, emp_id);
            return prepstmt.executeUpdate();
        }
    }

}
