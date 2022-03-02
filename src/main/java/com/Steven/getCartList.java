package com.Steven;

import java.io.BufferedReader;
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

@WebServlet("/getCartList")
public class getCartList extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		
		String input = null;
		StringBuilder reqBody = new StringBuilder();
		BufferedReader reader = req.getReader();
		while((input = reader.readLine()) != null){
			reqBody.append(input);
		}
		JSONObject datas = new JSONObject(reqBody.toString());
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		ResultSet resultSet = null;
		JSONArray returnData = new JSONArray();
		JSONObject returnJSON = new JSONObject();
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			String query = "SELECT name, price, amount, user, restaurantName, restaurantId FROM cartList WHERE user = ? ORDER BY name";
			PreparedStatement selectCMD = conn.prepareStatement(query);
			selectCMD.setString(1, datas.getString("user"));
			resultSet = selectCMD.executeQuery();
			while(resultSet.next()) {
				JSONObject product = new JSONObject();
				product.put("name", resultSet.getString(1));
				product.put("price", resultSet.getString(2));
				product.put("amount", resultSet.getString(3));
				product.put("user", resultSet.getString(4));
				product.put("restaurantName", resultSet.getString(5));
				product.put("restaurantId", resultSet.getString(6));
				returnData.put(product);
			}
			returnJSON.put("status", true);
			returnJSON.put("products", returnData);
			System.out.println(returnData.toString());
			out.println(returnJSON.toString());
			
		}catch(SQLException e) {
			returnJSON.put("status", false);
			System.out.println(returnData.toString());
			out.println(returnJSON.toString());
			e.printStackTrace();
		}
		
	}
}
