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


@Path("answers")
public class AnswersController {
	
	private static AnswersService answersService = new AnswersService();
	
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewAnswers(Answers answers) throws ClassNotFoundException {  
        
        return answersService.addAnswer(answers);
	}
	
	@GET
	@Path("/read")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAnswers(@QueryParam("answer_id") String answer_id) throws ClassNotFoundException, SQLException {  
        
        return answersService.readAnswers(answer_id).toString();

	}
	
	// Return List of Answer by Session_id is parameter
	@GET
	@Path("/readAll")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAllAnswersWithSessionId(@QueryParam("session_id") String session_id) throws ClassNotFoundException, SQLException {  
        
        return answersService.list_All_Answer_With_SessionId(session_id);

	}
	
	
//	@PUT
//	@Path("/update")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String updateAnswers(Answers questions) throws ClassNotFoundException, SQLException {  
//        
//        return questionsService.updateQuestion(questions);
//	}
//	
//	@POST
//	@Path("/delete")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String deleteAnswers(@QueryParam("question_id") String question_id) throws ClassNotFoundException, SQLException {  
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
//	
	
}
