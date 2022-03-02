package com.Steven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/sendOrder")
public class sendOrder extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		formData datas = new formData(req);
		
		ServletContext ctx = getServletContext();
		String connURL = ctx.getInitParameter("connectionURL");
		String connUser = ctx.getInitParameter("connectionUser");
		String connPwd = ctx.getInitParameter("connectionPassword");
		JSONObject returnData = new JSONObject();
		ResultSet resultSet = null;
		int currentId = 1;
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(connURL, connUser, connPwd);
			
			String ordersQuery = "INSERT INTO Orders (user, cost, time, restaurantName, restaurantId, note, address, card) VALUES (?,?,now(),?,?,?,?,?)";
			String detailsQuery = "INSERT INTO OrderDetail (id, product, price, amount) VALUES (?,?,?,?)";
			PreparedStatement ordersCmd = conn.prepareStatement(ordersQuery);
			PreparedStatement detailsCmd = conn.prepareStatement(detailsQuery);
			Iterator<String> keys = datas.keys();
			
			while(keys.hasNext()){
				String key = keys.next();
				JSONObject order = new JSONObject(datas.getString(key));
				ordersCmd.setString(1, order.getString("user"));
				ordersCmd.setString(2, order.getString("cost"));
				ordersCmd.setString(3, order.getString("restaurantName"));
				ordersCmd.setString(4, key);
				ordersCmd.setString(5, order.getString("note"));
				ordersCmd.setString(6, order.getString("address"));
				ordersCmd.setString(7, order.getString("card"));
				ordersCmd.execute();
				
				// update id
				String query = "SELECT MAX(id) AS 'last_id' FROM Orders";
				Statement statement = conn.createStatement();
				resultSet = statement.executeQuery(query);
				if(resultSet.next()) {
					currentId = resultSet.getInt(1);
				}
				
				JSONArray products = new JSONArray(order.getJSONArray("products"));
				for(int i = 0; i < products.length(); i++) {
					JSONObject product = products.getJSONObject(i);
					detailsCmd.setInt(1, currentId);
					detailsCmd.setString(2, product.getString("name"));
					detailsCmd.setString(3, product.getString("price"));
					detailsCmd.setString(4, product.getString("amount"));
					detailsCmd.execute();
				}
			}
			
			String query = "DELETE FROM cartList WHERE user = ?";
			PreparedStatement deleteCmd = conn.prepareStatement(query);
			deleteCmd.setString(1, new JSONObject(datas.getString("1")).getString("user"));
			deleteCmd.execute();
			
			returnData.put("status", true);
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
