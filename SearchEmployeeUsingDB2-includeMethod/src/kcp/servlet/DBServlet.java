package kcp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dburl")
public class DBServlet extends HttpServlet {
	private static final String query = "select empno,ename,job,sal from emp where empno=?";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int eno = 0;
		ServletContext sc = null;
		PrintWriter pw = null;
		RequestDispatcher rd1=req.getRequestDispatcher("headerurl");
		rd1.include(req,res);
		try {
			System.out.println("database to servlet communication");
			pw = res.getWriter();
			res.setContentType("text/html");
			eno = Integer.parseInt(req.getParameter("empno1"));
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "kalia");
			ps = con.prepareStatement(query);
			ps.setInt(1, eno);
			rs = ps.executeQuery();
			if (rs.next()) {
				pw.println("<br><br><br>");
				pw.println("<h1>Employee record is  found</h1>");
				pw.println("<br>Employee number:" + rs.getInt(1));
				pw.println("<br>Employee name:" + rs.getString(2));
				pw.println("<br>EMployee job:" + rs.getString(3));
				pw.println("<br>EMployee sal:" + rs.getFloat(4));
			} else {
				pw.println("<h1><b> record not found</b></h1>");
			}
			pw.println("<h2><br><a href='dbform.html'>Home</a></h2>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = req.getRequestDispatcher("/errorurl");
			rd.forward(req, res);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}

}
