package ctu.cit.anchaunhut;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("practiceSessions")
public class PracticeSessionsController {

	private static PracticeSessionsService practiceSessionsService = new PracticeSessionsService();

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewPracticeSessions(PracticeSessions practiceSessions) throws ClassNotFoundException {

		return practiceSessionsService.addNewPracticeSessions(practiceSessions);
	}

	@GET
	@Path("/read")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readPracticeSessions(@QueryParam("session_id") String session_id)
			throws ClassNotFoundException, SQLException {

		return practiceSessionsService.readPracticeSessions(session_id);

	}

////	Return List of Sessions by Quiz_id + User_id is parameter
	@GET
	@Path("/readAll")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAllSessionByUserIdvsQuizId(@QueryParam("quiz_id") String quiz_id,
			@QueryParam("user_id") String user_id) throws ClassNotFoundException, SQLException {
		return practiceSessionsService.readAllSessionByUserIdvsQuizId(quiz_id, user_id);

	}

//	@PUT
//	@Path("/update")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String updateQuestion(PracticeSessions questions) throws ClassNotFoundException, SQLException {  
//        
//        return questionsService.updateQuestion(questions);
//	}
//	
//	@POST
//	@Path("/delete")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String deleteQuestion(@QueryParam("question_id") String question_id) throws ClassNotFoundException, SQLException {  
//        
//        return questionsService.deleteQuestion(question_id);
//	}
//	
//	@GET
//	@Path("/deleteWithQuizId")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String delet_All_Question_With_QuizID(@QueryParam("quiz_id") String quiz_id) throws ClassNotFoundException, SQLException {  
//        
//        return questionsService.delete_All_Question_With_QuizID(quiz_id);
//	}
//	
//	@GET
//	@Path("/checkCorrect")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String checkCorrectQuestion(@QueryParam("question_id") String question_id, @QueryParam("Uanswer") String Uanswer) throws ClassNotFoundException, SQLException {  
//        
//        return questionsService.checkCorrectQuestion(question_id,Uanswer);
//	}

}
