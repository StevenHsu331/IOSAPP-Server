package com.Steven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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

@WebServlet("/getRestaurants")
public class getRestaurants extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();	
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		JSONObject returnData = new JSONObject();
		JSONArray restaurants = new JSONArray();
		ResultSet resultSet = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			String query = "SELECT id, name FROM restaurants";
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject restaurant = new JSONObject();
				restaurant.put("id", resultSet.getString(1));
				restaurant.put("name", resultSet.getString(2));
				restaurants.put(restaurant);
			}
			
			returnData.put("status", true);
			returnData.put("restaurants", restaurants);
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
