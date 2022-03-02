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

@WebServlet("/getUserInfo")
public class getUserInfo extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String ac = req.getParameter("user");
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		JSONObject returnData = new JSONObject();
		JSONArray addresses = new JSONArray();
		JSONArray cards = new JSONArray();
		ResultSet resultSet = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			String query = 
					"SELECT a.*, b.address, c.code "
					+ "From memberInfo as a "
					+ "LEFT JOIN address as b ON a.account = b.user "
					+ "LEFT JOIN creditCards as c ON a.account= c.user "
					+ "WHERE account = ?";
			PreparedStatement selectCmd = conn.prepareStatement(query);
			selectCmd.setString(1, ac);
			resultSet = selectCmd.executeQuery();
			while(resultSet.next()) {
				if (resultSet.getRow() == 1) {
					returnData.put("name", resultSet.getString(2));
					returnData.put("number", resultSet.getString(6));
				}
				
				String currentAddress = resultSet.getString(8);
				if(!resultSet.wasNull()) {
					JSONObject address = new JSONObject();
					addresses.put(currentAddress);
				}
				
				String currentCard = resultSet.getString(9);
				if(!resultSet.wasNull()) {
					JSONObject card = new JSONObject();
					cards.put(currentCard);
				}
			}
			returnData.put("status", true);
			returnData.put("addresses", addresses);
			returnData.put("cards", cards);
			System.out.println(returnData.toString());
			out.println(returnData.toString());
		}
		catch(SQLException e){
			e.printStackTrace();
			returnData.put("status", false);
			System.out.println(returnData.toString());
			out.println(returnData.toString());
		}
	}
}
