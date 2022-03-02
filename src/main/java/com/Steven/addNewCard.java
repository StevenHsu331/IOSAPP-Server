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

@WebServlet("/addNewCard")
public class addNewCard extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		formData datas = new formData(req);
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		JSONObject returnData = new JSONObject();
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			String query = "INSERT INTO CreditCards (user, code, safetyCode, expireDate) VALUES (?, ?, ?, ?)";
			PreparedStatement insertCMD = conn.prepareStatement(query);
			insertCMD.setString(1, datas.getString("user"));
			insertCMD.setString(2, datas.getString("code"));
			insertCMD.setString(3, datas.getString("safetyCode"));
			insertCMD.setString(4, datas.getString("expireDate"));
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
