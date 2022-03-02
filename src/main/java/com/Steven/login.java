package com.Steven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/login")
public class login extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();
		ServletContext ctx = getServletContext();
//		Cookie cookie = new Cookie("test", "value");
//		res.addCookie(cookie);
		
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		ResultSet resultSet = null;
		boolean verify = false;
		JSONObject returnData = new JSONObject();
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			Statement statement = conn.createStatement();
			String query = "SELECT account, password FROM memberInfo";
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				if(resultSet.getString(1).equals(account)) {
					returnData.put("account", true);
					if(resultSet.getString(2).equals(password)) {
						verify = true;
						session.setAttribute("verify", true);
						session.setAttribute("user", account);
						returnData.put("password", true);
					}
					else {
						session.setAttribute("verify", false);
						returnData.put("password", false);
					}
					break;
				}
			}
			if(!returnData.has("account")) {
				returnData.put("account", false);
				returnData.put("password", false);
			}
			returnData.put("status", verify);
			out.println(returnData.toString());
		} catch (SQLException e) {
			returnData.put("status", false);
			out.println(returnData.toString());
			e.printStackTrace();
		}
		
		// res.sendRedirect("/WebApp/testSession");
	}
}
