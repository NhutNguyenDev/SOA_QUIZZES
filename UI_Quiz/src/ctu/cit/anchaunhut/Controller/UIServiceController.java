package ctu.cit.anchaunhut.Controller;

import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import ctu.cit.anchaunhut.Handle.UiService_Component;

public class UIServiceController {
	
	public UIServiceController() {
		super();
	}

	UiService_Component UiService = new UiService_Component();

	public void takeTheExam(String quiz_id, String user_id, PrintWriter out) {
		UiService.takeTheExam(quiz_id, user_id, out);
	}

	public void getAllSessionByQuiz_id(PrintWriter out, String user_id, String quiz_id) {
		UiService.getAllSessionByQuiz_id(out, user_id, quiz_id);
	}

	public void GetQuestionById(PrintWriter out, String question_id, Boolean is_correct, String user_answer) {
		UiService.GetQuestionById(out, question_id, is_correct, user_answer);
	}

	public String StartSessionExam(String user_id, String quiz_id) {
		return UiService.StartSessionExam(user_id, quiz_id);
	}

	public void script(PrintWriter out) {
		UiService.script(out);
	}
	
	public void getAllQuiz(PrintWriter out, String user_id) {
		UiService.getAllQuiz(out, user_id);
	}

	public void css(PrintWriter out) {
		UiService.css(out);
	}
	
	public void cssHomePage(PrintWriter out) {
		UiService.cssHomePage(out);
	}
	
	
	// First HTML of Login Page
	public void loginPage(PrintWriter out) {
		UiService.loginPage(out);
	}
	
	public void cssLogin(PrintWriter out) {
		UiService.cssLogin(out);
	}
	
}
