package ctu.cit.anchaunhut;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import ctu.cit.anchaunhut.Controller.UIServiceController;

@WebServlet("/index")
public class index extends HttpServlet {

	private static final long serialVersionUID = 1L;
//	UiService_Component uiService = new UiService_Component();
	PrintWriter out = null;
	
	UIServiceController UiService = new UIServiceController();

	public index() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		Create instance of PrintWriter
		out = response.getWriter();
		
//		Title Page
		out.println("<html><head><title>Input Numbers</title></head><body>");

//		Get Quiz with id
		
		String quiz_id = "11";
		String user_id = "1";
		
		UiService.getAllSessionByQuiz_id(out, user_id, quiz_id);
		out.println("<h1>================================================================</h1>");
		
		UiService.takeTheExam(quiz_id, user_id, out);
		

//		Import CSS
		UiService.css(out);
		UiService.script(out);


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
