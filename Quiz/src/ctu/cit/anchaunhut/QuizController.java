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


@Path("quiz")
public class QuizController {
	
	private static QuizService quizService = new QuizService();
	
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewQuiz(Quiz Quiz) throws ClassNotFoundException {  
        return quizService.addQuiz(Quiz);
	}
	
	@GET
	@Path("/read")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readQuiz(@QueryParam("quiz_id") String quiz_id) throws ClassNotFoundException, SQLException {  
        
        return quizService.readQuiz(quiz_id);

	}
	
	@GET
	@Path("/readAllQuiz")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAllQuiz() throws ClassNotFoundException, SQLException {  
        
        return quizService.readAllQuiz();

	}
	
//////	Return List of Options by Question_id is parameter
//////	@GET
//////	@Path("/readAll")
//////	@Consumes(MediaType.APPLICATION_XML)
//////	@Produces(MediaType.APPLICATION_JSON)
//////	public String readAllQuestionsWithQuestionId(@QueryParam("id") String options_id) throws ClassNotFoundException, SQLException {  
//////        
//////        return questionsService.readAllOptionByQuestionId(options_id);
//////
//////	}
////	
////	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuiz(Quiz quiz) throws ClassNotFoundException, SQLException {  
        
    return quizService.updateQuiz(quiz);
	}
	
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
public String deleteQuiz(@QueryParam("quiz_id") String quiz_id) throws ClassNotFoundException, SQLException {  
        
        return quizService.deleteQuiz(quiz_id);
	}
	
	
}
