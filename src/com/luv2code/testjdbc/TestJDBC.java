package com.luv2code.testjdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestJDBC
 */
@WebServlet("/TestJDBC")
public class TestJDBC extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String username="springstudent";
		String password="springstudent";
		
		String jdbcURL= "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";
		String driver= "com.mysql.cj.jdbc.Driver";
		//get connnection to database
		try {
			PrintWriter out=response.getWriter();
			out.println("Connecting to database \n"+ jdbcURL);
			Class.forName(driver);
			Connection myConn=DriverManager.getConnection(jdbcURL,username,password);
			out.println("\nSUCESS");
		
			myConn.close();
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
			throw new ServletException(exc);
		}
	}

}
