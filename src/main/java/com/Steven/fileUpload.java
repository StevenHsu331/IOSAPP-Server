package com.Steven;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/fileUpload")
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 100,      // 100 MB
  maxRequestSize = 1024 * 1024 * 500   // 500 MB
)
public class fileUpload extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		Part profilePart = req.getPart("profile");
		Part menuPart = req.getPart("menu");
		String profileName = profilePart.getSubmittedFileName();
		String menuName = menuPart.getSubmittedFileName();
		
		if(profileName != null) {
			for(Part part: req.getParts()) {
				part.write("/Users/steven_hsu/Java/WebApp/src/main/webapp/image/" + profileName);
			}
		}
		
		if(menuName != null) {
			for(Part part: req.getParts()) {
				part.write("/Users/steven_hsu/Java/WebApp/src/main/webapp/menu/" + menuName);
			}
		}
	}
}
