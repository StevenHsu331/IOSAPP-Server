package com.Steven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/addNewAddress")
public class addNewAddress extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String user = req.getParameter("user");
		String address = req.getParameter("address");
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		JSONObject returnData = new JSONObject();
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			String query = "INSERT INTO Address (user, address) VALUES (?, ?)";
			PreparedStatement insertCMD = conn.prepareStatement(query);
			insertCMD.setString(1, user);
			insertCMD.setString(2, address);
			insertCMD.execute();
			returnData.put("status", true);
			out.println(returnData.toString());
		}
		catch(SQLException e){
			e.printStackTrace();
			returnData.put("status", false);
			out.println(returnData.toString());
		}
		
	}
}
