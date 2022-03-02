package com.Steven;

import java.util.Enumeration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.*;


public class formData extends JSONObject{
	
	public formData(HttpServletRequest req) {
		Enumeration<String> sources = req.getParameterNames();
		while(sources.hasMoreElements()) {
			String name = sources.nextElement();
			this.put(name, req.getParameter(name));
		}
	}
}
