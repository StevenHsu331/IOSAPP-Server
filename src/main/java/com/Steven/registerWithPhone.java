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

@WebServlet("/registerIOS")
public class registerWithPhone extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		
		String input = null;
		StringBuilder reqBody = new StringBuilder();
		BufferedReader reader = req.getReader();
		while((input = reader.readLine()) != null) {
			reqBody.append(input);
		}
		JSONObject datas = new JSONObject(reqBody.toString());
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		ResultSet resultSet = null;
		boolean verify = true;
		JSONObject returnData = new JSONObject();
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM memberInfo ORDER BY id DESC";
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				if(datas.getString("account").equals(resultSet.getString(3))) {
					verify = false;
					returnData.put("account", false);
				}
				if(datas.getString("password").equals(resultSet.getString(4))) {
					verify = false;
					returnData.put("password", false);
				}
				if(datas.getString("number").equals(resultSet.getString(6))) {
					verify = false;
					returnData.put("number", false);
				}
			}
			
			if(!returnData.has("account")) {
				returnData.put("account", true);
			}
			if(!returnData.has("password")) {
				returnData.put("password", true);
			}
			if(!returnData.has("number")) {
				returnData.put("number", true);
			}
			returnData.put("status", verify);
			
			if(verify) {
				query = "INSERT INTO memberInfo (name, account, password, number, gender) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insertCMD = conn.prepareStatement(query);
				insertCMD.setString(1, datas.getString("name"));
				insertCMD.setString(2, datas.getString("account"));
				insertCMD.setString(3, datas.getString("password"));
				insertCMD.setString(4, datas.getString("number"));
				insertCMD.setString(5, datas.getString("gender"));
				insertCMD.execute();
			}
			
			out.println(returnData.toString());
		}
		catch(SQLException e) {
			returnData.put("status", false);
			out.println(returnData.toString());
			e.printStackTrace();
		}
	}
}
