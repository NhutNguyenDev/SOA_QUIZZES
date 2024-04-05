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

public class UsersService {

	private static db db = new db();

	public String addUsers(Users question) throws ClassNotFoundException {

		String sql = "INSERT INTO Questions (question_text, quiz_id) VALUES (?, ?)";

		Connection connection = db.getConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, question.getQuestion_text());
//			preparedStatement.setString(2, question.getQuiz_id());
			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				connection.close();
				return "A new \\ QUESTION \\ was added successfully.";
			} else {
				return "Failed to add new \\\\ QUESTION \\\\.";
			}
		} catch (SQLException e) {
			return "PreparedStatement \\\\ QUESTION \\\\ not work !!! : " + e;
		}

	}

	public String authentication(Users user) throws SQLException, ClassNotFoundException {

		Users userOutput = null;
		String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, user.getUserName());
		statement.setString(2, user.getPassWord());

		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				userOutput = new Users();

				userOutput.setUser_id(resultSet.getString("user_id"));
				userOutput.setUserName(resultSet.getString("username"));
				userOutput.setPassWord(resultSet.getString("password"));
				userOutput.setEmail(resultSet.getString("email"));
//				userOutput.setCreated_at(resultSet.getDate("quiz_id"));

			} else {
				return "LoginFail";
			}

		} catch (Exception e) {
			System.out.println("authentication - Resultset Don't work !!! :" + e);
		}
		connection.close();

		if(userOutput.getUser_id().compareTo("1") == 0) {
			return "adminLogin";
		}
		return userOutput.toString();
	}
//	public String readQuestion(String question_id) throws ClassNotFoundException, SQLException {
//
//		Users question = null;
//		String sql = "SELECT * FROM Questions WHERE question_id = ?";
//
//		Connection connection = db.getConnection();
//
//		PreparedStatement statement = connection.prepareStatement(sql);
//
//		statement.setString(1, question_id);
//		try (ResultSet resultSet = statement.executeQuery()) {
//			if (resultSet.next()) {
//				question = new Users();
//				question.setQuestion_id(resultSet.getString("question_id"));
//				question.setQuestion_text(resultSet.getString("question_text"));
//				question.setQuiz_id(resultSet.getString("quiz_id"));
//			}
//
//		} catch (Exception e) {
//			System.out.println("Resultset Don't work !!! :" + e);
//		}
//		connection.close();
//
//		return question.readWithOptions(question_id);
//
//	}
//
//	public String updateQuestion(Users question) throws SQLException {
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
//	public String list_All_Question_With_QuizID(String quiz_id) throws SQLException, ClassNotFoundException{
//		
//		List<String> listQuestion = new ArrayList<>();
//		
//
//		String sql = "SELECT * FROM Questions WHERE quiz_id = ?";
//
//		Connection connection = db.getConnection();
//
//		PreparedStatement statement = connection.prepareStatement(sql);
//
//		statement.setString(1, quiz_id);
//		
//		try (ResultSet resultSet = statement.executeQuery()) {
//			while (resultSet.next()) {
//				listQuestion.add(this.readQuestion(resultSet.getString("question_id")));
//			}
//
//		} catch (Exception e) {
//			System.out.println("Resultset Don't work !!! :" + e);
//		}
//		connection.close(); 
//		
//		return listQuestion.toString();
//	}
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
