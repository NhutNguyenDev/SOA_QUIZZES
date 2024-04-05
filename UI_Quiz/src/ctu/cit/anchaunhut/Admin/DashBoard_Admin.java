package ctu.cit.anchaunhut.Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import ctu.cit.anchaunhut.Controller.AdminController;
import ctu.cit.anchaunhut.Controller.UIServiceController;
import ctu.cit.anchaunhut.Handle.AdminService;

@WebServlet("/DashBoard_Admin")
public class DashBoard_Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UIServiceController UiService = new UIServiceController();
	AdminController adminController = new AdminController();
	PrintWriter out = null;



	public DashBoard_Admin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		
		out = response.getWriter();
//		Map<String, String[]> parameterMap = request.getParameterMap();

		PrintWriter out = response.getWriter();

		// Get value of new Quiz
//		this.getInformation(parameterMap);

		out.println("<h1> Admin DashBoard </h1>");

		out.println("<a class=\"button\" onclick=\"addNewQuiz()\">Add New Quiz</a><br>");
		
		// Form Add new Quiz
		formAddNewQuiz();

		// Read all Quiz - each quiz have it own box.
		adminController.readAllQuiz(out);

//		Import CSS + JS

		adminController.cssDashboardAdmin(out);
		adminController.scriptDashboardAdmin(out);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void formAddNewQuiz() {
		out.println("<br><div id=\"quizFormDiv1\">");
		out.println(
				"<form id=\"quizFormDiv\" style=\"display:none; \" action=\"/UI_Quiz/handle_addNewQuiz\" method=\"POST\">");
		out.println("    <input type=\"hidden\" id=\"creator_id\" name=\"creator_id\" value=\"1\">");
		out.println("");
		out.println("    <label for=\"quiz_title\">Quiz Title:</label><br>");
		out.println("    <input type=\"text\" id=\"quiz_title\" name=\"quiz_title\"><br><br>");
		out.println("");
		out.println("    <label for=\"quiz_description\">Quiz Description:</label><br>");
		out.println("    <textarea id=\"quiz_description\" name=\"quiz_description\"></textarea><br><br>");
		out.println("");
		out.println("    <input type=\"submit\" value=\"Submit\">");
		out.println("</form>");
		out.println("</div>");
	}

//	private void getInformation(Map<String, String[]> parameterMap) {
//		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
//
//			String parameterName = entry.getKey();
//			int checkquiz_title = 0;
//			int checkquiz_description = 0;
//
//			if (parameterName.compareTo("quiz_title") == 0) {
//				checkquiz_title = 1;
//			} else if (parameterName.compareTo("quiz_description") == 0) {
//				checkquiz_description = 1;
//			}
//
////	    	ParamValue must be String[], it can have OBJ inside
//			String[] paramValues = entry.getValue();
//
//			for (String paramValue : paramValues) {
//				if (checkquiz_title == 1) {
//					quiz_title = paramValue;
//				} else if (checkquiz_description == 1) {
//					quiz_description = paramValue;
//				} else{
//					creator_id = paramValue;
//				}
//			}
//
//		}
//		if(quiz_title != "" && quiz_description != "" && creator_id != "") {
//			ClientConfig config = new ClientConfig();
//			Client client = ClientBuilder.newClient(config);
//
//			URI uri = UriBuilder.fromUri("http://localhost:8080/Quiz/api/quiz/new").build();
//
//			WebTarget target = client.target(uri);
//
//			// Create a JSON object representing your request data
//			JsonObject requestData = Json.createObjectBuilder()
//					.add("quiz_title", quiz_title)
//					.add("quiz_description", quiz_description)
//					.add("creator_id", creator_id)
//					.build();
//
//			// Send a POST request with the JSON data
//			Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));
//
//			// Read the response body
//			String jsonResponse = response.readEntity(String.class);
//
//			// Close the response
//			response.close();
//			
////			out.println(jsonResponse);
//		}
//	}

}
