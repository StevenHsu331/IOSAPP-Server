package com.Steven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/getProfile")
public class getProfile extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		String user = req.getParameter("user");
		String imagePath = "/Users/steven_hsu/Java/WebApp/src/main/webapp/profile/";
		JSONObject returnData = new JSONObject();
		
		String base64Image = "";
		File file = new File(imagePath + user + "_profile.png");
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		
		returnData.put("status", true);
		returnData.put("profile", base64Image);
		//System.out.println(returnData.toString());
		out.println(returnData.toString());
	}
}
