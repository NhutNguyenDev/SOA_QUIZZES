package ctu.cit.anchaunhut;

import java.io.StringReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

public class PracticeSessionsService {

	private static db db = new db();

	public String addNewPracticeSessions(PracticeSessions practiceSessions) throws ClassNotFoundException {

		String sql = "INSERT INTO PracticeSessions (user_id, quiz_id) VALUES (?, ?)";

		Connection connection = db.getConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, practiceSessions.getUser_id());
			preparedStatement.setString(2, practiceSessions.getQuiz_id());
			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int session_id = generatedKeys.getInt(1); // Assuming session_id is of type INT
					connection.close();
					return "" + session_id;
				} else {
					connection.close();
					return "Failed to retrieve session_id after inserting new PRACTICE_SESSION.";
				}
			} else {
				connection.close();
				return "Failed to add new PRACTICE_SESSION.";
			}
		} catch (SQLException e) {
			return "PreparedStatement PRACTICE_SESSION not work: " + e.getMessage();
		}

	}

	public String readPracticeSessions(String session_id) throws ClassNotFoundException, SQLException {

		PracticeSessions practiceSessions = null;
		String sql = "SELECT * FROM PracticeSessions WHERE session_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, session_id);
		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				practiceSessions = new PracticeSessions();
				practiceSessions.setSession_id(resultSet.getString("session_id"));
				practiceSessions.setUser_id(resultSet.getString("user_id"));
				practiceSessions.setQuiz_id(resultSet.getString("quiz_id"));

				Timestamp startTimestamp = resultSet.getTimestamp("start_date");
				if (startTimestamp != null) {
					LocalDateTime startDateTime = startTimestamp.toLocalDateTime();
					practiceSessions.setStart_date(startDateTime);
				}

				// Retrieving end_date as LocalDateTime
				Timestamp endTimestamp = resultSet.getTimestamp("end_date");
				if (endTimestamp != null) {
					LocalDateTime endDateTime = endTimestamp.toLocalDateTime();
					practiceSessions.setEnd_date(endDateTime);
				}

			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work !!! :" + e);
		}
		connection.close();
		return practiceSessions.readWithAnswer(session_id);
//		return practiceSessions.readWithOptions(session_id);

	}

	// This function support readWithAnswer() in PracticeSession - purpose return
	// String Json data of all Answer with Session_id is parameter
	public static String read_All_Answers_With_SessionId(String session_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Answers/api/answers/readAll").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("session_id", session_id).request().accept(MediaType.APPLICATION_JSON)
				.get(String.class);

		return response;
	}

//	public String updateQuestion(PracticeSessions question) throws SQLException {
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

	public String readAllSessionByUserIdvsQuizId(String quiz_id, String user_id) throws SQLException, ClassNotFoundException {

		
		List<String> listPracticeSession = new ArrayList<>();
		
		String sql = "SELECT * FROM PracticeSessions WHERE quiz_id = ? && user_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, quiz_id);
		statement.setString(2, user_id);
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				String session_id = resultSet.getString("session_id");

				System.out.println("Call \"readPracticeSessions\" with session_id: " + session_id);
				listPracticeSession.add(this.readPracticeSessions(session_id));
//				PracticeSessions practiceSessions = null;
//
//				practiceSessions = new PracticeSessions();
//				practiceSessions.setSession_id(resultSet.getString("session_id"));
//				practiceSessions.setUser_id(resultSet.getString("user_id"));
//				practiceSessions.setQuiz_id(resultSet.getString("quiz_id"));
//				Timestamp startTimestamp = resultSet.getTimestamp("start_date");
//				if (startTimestamp != null) {
//					LocalDateTime startDateTime = startTimestamp.toLocalDateTime();
//					practiceSessions.setStart_date(startDateTime);
//				}
//
//				// Retrieving end_date as LocalDateTime
//				Timestamp endTimestamp = resultSet.getTimestamp("end_date");
//				if (endTimestamp != null) {
//					LocalDateTime endDateTime = endTimestamp.toLocalDateTime();
//					practiceSessions.setEnd_date(endDateTime);
//				}

			}

		} catch (Exception e) {
			System.out.println("readAllSessionByUserIdvsQuizId - Resultset Don't work !!! :" + e);
		}
		connection.close();

		return listPracticeSession.toString();
	}
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
