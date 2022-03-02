package com.Steven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/getOrderDetails")
public class getOrderDetails extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String user = req.getParameter("user");
		String id = req.getParameter("id");
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		JSONObject returnData = new JSONObject();
		JSONArray details = new JSONArray();
		ResultSet resultSet = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			String query = "SELECT product, price, amount FROM OrderDetail WHERE id = ?";
			PreparedStatement selectCmd = conn.prepareStatement(query);
			selectCmd.setInt(1, Integer.parseInt(id));
			resultSet = selectCmd.executeQuery();
			while(resultSet.next()) {
				JSONObject detail = new JSONObject();
				detail.put("name", resultSet.getString(1));
				detail.put("price", resultSet.getString(2));
				detail.put("amount", resultSet.getString(3));
				details.put(detail);
			}
			returnData.put("status", true);
			returnData.put("products", details);
			System.out.println(returnData.toString());
			out.println(returnData.toString());
		}
		catch(SQLException e) {
			e.printStackTrace();
			returnData.put("status", false);
			System.out.println(returnData.toString());
			out.println(returnData.toString());
		}
	}
}
