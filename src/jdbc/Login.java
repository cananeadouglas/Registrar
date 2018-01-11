package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		try {
			String login = request.getParameter("user"); // user e password vem da página login.jsp
			String senha = request.getParameter("password");
			String dbName = null;
			String dbPassword = null;
			String sql = "select * from olad where login=? and senha=?";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "123456");
			java.sql.PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, login);
			ps.setString(2, senha);

			ResultSet rs = ps.executeQuery();
			PrintWriter out = response.getWriter();
			while (rs.next()) {
				dbName = rs.getString(2); // coluna 2 referente ao db;
				dbPassword = rs.getString("senha");
			}

			if (login.equals(dbName) && senha.equals(dbPassword)) {
				out.println("LOGIN com sucesso");

				HttpSession session = request.getSession();
				session.setAttribute("login", login); // login vai esta na pagina home para puchar o login
				response.sendRedirect("home.jsp");
			} else {

				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.include(request, response);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
