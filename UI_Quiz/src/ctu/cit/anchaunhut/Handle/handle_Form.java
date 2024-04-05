package ctu.cit.anchaunhut.Handle;

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

@WebServlet("/handle_Form")
public class handle_Form extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public handle_Form() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String[]> parameterMap = request.getParameterMap();

		PrintWriter out = response.getWriter();

//		Element 
		String user_id = "";
		String quiz_id = "";
		String session_id = "";

		int countCorrect = 0;
		// Iterate over the parameter map and print parameter names and values
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {

			// Get id of User_id
			if (entry.getKey().compareTo("user_id") == 0) {

				String[] paramValues = entry.getValue();
				for (String paramValue : paramValues) {
					user_id = paramValue;
				}

				// Get quiz_id
			} else if (entry.getKey().compareTo("quiz_id") == 0) {

				String[] paramValues = entry.getValue();
				for (String paramValue : paramValues) {
					quiz_id = paramValue;
				}
				// Get Question and User_Answer + Check correct
			} else if (entry.getKey().compareTo("session_id") == 0) {

				String[] paramValues = entry.getValue();
				for (String paramValue : paramValues) {
					session_id = paramValue;
				}
				// Get Question and User_Answer + Check correct
			} else {

				String question_id = entry.getKey();
				String userAnswer = "";

//	    	ParamValue must be String[], it can have OBJ inside
				String[] paramValues = entry.getValue();

				for (String paramValue : paramValues) {
					userAnswer = paramValue;
				}

				// Check correct for each question
				if (checkCorrectAnser(question_id, userAnswer).compareTo("true") == 0) {
					countCorrect++;
					// Add new Answer with each question ( TRUE answer )
					out.println(addAnswer(session_id, question_id, userAnswer, "true"));
				}
				// Add new Answer with each question ( FALSE answer )
				out.println(addAnswer(session_id, question_id, userAnswer, "false"));
			}
		}
		out.println("So cau dung: " + countCorrect);
		out.println("session_id:" + session_id);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// Call API of Question check correct or false
	static String checkCorrectAnser(String question_id, String userAnswer) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Questions/api/questions/checkCorrect").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("question_id", question_id).queryParam("Uanswer", userAnswer).request()
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}

	static String addAnswer(String session_id, String question_id, String user_answer, String is_correct) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Answers/api/answers/new").build();

		WebTarget target = client.target(uri);

		// Create a JSON object representing your request data
		JsonObject requestData = Json.createObjectBuilder().add("session_id", session_id)
				.add("question_id", question_id).add("user_answer", user_answer).add("is_correct", is_correct).build();

		// Send a POST request with the JSON data
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));

		// Read the response body
		String jsonResponse = response.readEntity(String.class);

		// Close the response
		response.close();

		return "Add new Answer session_id: " + session_id + " question_id: " + question_id + " user_answer: "
				+ user_answer + " is_correct: " + is_correct;
	}
}
