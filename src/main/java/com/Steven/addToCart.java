package com.Steven;

import java.io.BufferedReader;
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

import org.json.JSONObject;

@WebServlet("/addToCart")
public class addToCart extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		formData datas = new formData(req);
		
		// traditional way to get data from ios app is to read the whole body
//		String input = null;
//		StringBuilder reqBody = new StringBuilder();
//		BufferedReader reader = req.getReader();
//		while((input = reader.readLine()) != null){
//			reqBody.append(input);
//		}
//		JSONObject datas = new JSONObject(reqBody.toString());
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		ResultSet resultSet = null;
		JSONObject returnData = new JSONObject();
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			Statement statement = conn.createStatement();
			String query = "SELECT amount FROM cartList WHERE user = ? AND name = ? AND restaurantId = ?";
			PreparedStatement selectCMD = conn.prepareStatement(query);
			selectCMD.setString(1, datas.getString("user"));
			selectCMD.setString(2, datas.getString("name"));
			selectCMD.setString(3, datas.getString("restaurantId"));
			resultSet = selectCMD.executeQuery();
			Integer amount = Integer.parseInt(datas.getString("amount"));
			if(resultSet.next()) {
				amount += Integer.parseInt(resultSet.getString(1));
				query = "DELETE FROM cartList WHERE user = ? AND name = ? AND restaurantId = ?";
				PreparedStatement deleteCMD = conn.prepareStatement(query);
				deleteCMD.setString(1, datas.getString("user"));
				deleteCMD.setString(2, datas.getString("name"));
				deleteCMD.setString(3, datas.getString("restaurantId"));
				deleteCMD.execute();
			}
			
			query = "INSERT INTO cartList (user, name, price, amount, restaurantName, restaurantId) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement insertCMD = conn.prepareStatement(query);
			insertCMD.setString(1, datas.getString("user"));
			insertCMD.setString(2, datas.getString("name"));
			insertCMD.setString(3, datas.getString("price"));
			insertCMD.setString(4, amount.toString());
			insertCMD.setString(5, datas.getString("restaurantName"));
			insertCMD.setString(6, datas.getString("restaurantId"));
			insertCMD.execute();
			returnData.put("status", true);
			System.out.println(returnData.toString());
			out.println(returnData.toString());
		}catch(SQLException e) {
			e.printStackTrace();
			returnData.put("status", false);
			System.out.println(returnData.toString());
			out.println(returnData.toString());
		}
	}
}
