package com.Steven;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/getOrders")
public class getOrders extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String user = req.getParameter("user");
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		JSONObject returnData = new JSONObject();
		JSONArray orders = new JSONArray();
		ResultSet resultSet = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			String query = "SELECT id, cost, time, restaurantName, restaurantId FROM orders WHERE user = ?";
			PreparedStatement selectCmd = conn.prepareStatement(query);
			selectCmd.setString(1, user);
			
			resultSet = selectCmd.executeQuery();
			while(resultSet.next()) {
				JSONObject order = new JSONObject() {
					
				};
				order.put("id", resultSet.getInt(1));
				order.put("cost", resultSet.getString(2));
				order.put("time", resultSet.getString(3));
				order.put("restaurantName", resultSet.getString(4));
				order.put("restaurantId", resultSet.getString(5));
				orders.put(order);
			}
			returnData.put("status", true);
			returnData.put("orders", orders);
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
