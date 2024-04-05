package ctu.cit.anchaunhut;

import java.io.StringReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class AnswersService {

	private static db db = new db();

	public String addAnswer(Answers answer) throws ClassNotFoundException {

		String sql = "INSERT INTO Answers (session_id, question_id, user_answer, is_correct) VALUES (?, ?, ?, ?)";

		Connection connection = db.getConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, answer.getSession_id());
			preparedStatement.setString(2, answer.getQuestion_id());
			preparedStatement.setString(3, answer.getUser_answer());
			preparedStatement.setBoolean(4, answer.getIs_correct());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				connection.close();
				return "A new \\ addAnswer \\ was added successfully.";
			} else {
				return "Failed to add new \\\\ addAnswer \\\\.";
			}
		} catch (SQLException e) {
			return "PreparedStatement \\\\ addAnswer \\\\ not work !!! : " + e;
		}

	}

	public Answers readAnswers(String answer_id) throws ClassNotFoundException, SQLException {

		Answers answer = null;
		String sql = "SELECT * FROM Answers WHERE answer_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, answer_id);
		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				answer = new Answers();
				answer.setAnswer_id(resultSet.getString("answer_id"));
				answer.setSession_id(resultSet.getString("session_id"));
				answer.setQuestion_id(resultSet.getString("question_id"));
				answer.setUser_answer(resultSet.getString("user_answer"));
				answer.setIs_correct(resultSet.getBoolean("is_correct"));
			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work !!! :" + e);
		}
		connection.close();

		return answer;

	}
	

//
//	public String updateQuestion(Answers question) throws SQLException {
//		String sql = "UPDATE Questions SET quiz_id = ?, question_text = ? WHERE question_id = ?";
//		Connection connection = null;
//		try {
//			connection = db.getConnection();
//		} catch (ClassNotFoundException e) {
//			System.out.println("Can't create connection to db in \"updateQuestion\" + : " + e);
//		}
//		try (PreparedStatement statement = connection.prepareStatement(sql)) {
//			statement.setString(1, question.getQuiz_id());
//			statement.setString(2, question.getQuestion_text());
//			statement.setString(3, question.getQuestion_id());
//
//			statement.executeUpdate();
//		}
//		return "update \"QUESTION\" success ";
//	}
//
//	public String deleteQuestion(String question_id) {
//		String sql = "DELETE FROM Questions WHERE question_id = ?";
//		String sqlDeleteOptions = "DELETE FROM Options WHERE question_id = ?";
//
//		Connection connection = null;
//
//		try {
//			connection = db.getConnection();
//
////			Delete all options with questions_id
//			PreparedStatement statementDeleteOptions = connection.prepareStatement(sqlDeleteOptions);
//			statementDeleteOptions.setString(1, question_id);
//			statementDeleteOptions.executeUpdate();
//
////			Delete question
//			PreparedStatement statement = connection.prepareStatement(sql);
//			statement.setString(1, question_id);
//			statement.executeUpdate();
//
//		} catch (ClassNotFoundException e) {
//			return "Can't create connection to db in \"deleteQuestion\"";
//		} catch (SQLException e) {
//			return "PreparedStatement in deleteQuestion not work !!! : " + e;
//
//		}
//
//		return "Delete \"QUESTION\" Success ";
//	}
//
//	public String delete_All_Question_With_QuizID(String quiz_id) throws ClassNotFoundException, SQLException {
//
//		String sql = "SELECT * FROM Questions WHERE quiz_id = ?";
//
//		Connection connection = db.getConnection();
//
//		PreparedStatement statement = connection.prepareStatement(sql);
//
//		try {
//			statement.setString(1, quiz_id);
//
//			ResultSet resultSet = statement.executeQuery();
//
//			while (resultSet.next()) {
//				this.deleteQuestion(resultSet.getString("question_id"));
//			}
//		} catch (Exception e) {
//			System.out.println("Resultset Don't work !!! :" + e);
//		}
//
//		return "Delete All Question with Quiz_id SUCCESS";
//
//	}
//
////	This is call API of Options - return String JSON type
//	protected static String read_All_Option_With_Question_Id(String question_id) {
//		ClientConfig config = new ClientConfig();
//		Client client = ClientBuilder.newClient(config);
//
//		URI uri = UriBuilder.fromUri("http://localhost:8080/Z_Options/api/options/readAll").build();
//
//		WebTarget target = client.target(uri);
//
//		String response = target.queryParam("question_id", question_id).request().accept(MediaType.APPLICATION_JSON)
//				.get(String.class);
//
//		return response;
//	}
//	
	public String list_All_Answer_With_SessionId(String session_id) throws SQLException, ClassNotFoundException{
		
		List<Answers> listQuestion = new ArrayList<>();
		

		String sql = "SELECT * FROM Answers WHERE session_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, session_id);
		
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				listQuestion.add(this.readAnswers(resultSet.getString("answer_id")));
			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work !!! :" + e);
		}
		connection.close(); 
		
		return listQuestion.toString();
	}
//
////	Uanser is "ID OPTION " id choose for "ID QUESTION"
//	public String checkCorrectQuestion(String question_id, String Uanswer) throws ClassNotFoundException, SQLException {
//
//		String dataQuestion = readQuestion(question_id);
//		
//		// Create a StringReader object
//		StringReader stringReader = new StringReader(dataQuestion);
//
//		// Create a JsonReader object from StringReader
//		JsonReader jsonReader = Json.createReader(stringReader);
//
//		// Get Question OBJ from JsonReader
//		JsonObject jsonQuestion = jsonReader.readObject();
//		
//		// Get Options data - contatin array of multiple option		
//		JsonArray jsonOptions = jsonQuestion.getJsonArray("options");
//		
//		// Loop each option.
//		for(int i = 0; i < jsonOptions.size(); i++) {
//			
//			// Get option
//			JsonObject jsonOption = jsonOptions.getJsonObject(i);
//			
//		// Check if option is_correct = true, continue check if "User Answer" = this option => Return True
//			if(jsonOption.getBoolean("is_correct") == true) {
//				if(jsonOption.getString("option_id").compareTo(Uanswer) == 0) {
//					return "true";
//				}
//			}
//
//		}
//
//		return "false";
//	}

}
