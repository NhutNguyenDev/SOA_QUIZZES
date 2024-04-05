package ctu.cit.anchaunhut;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ctu.cit.anchaunhut.Controller.UIServiceController;

@WebServlet("/loginPage")
public class loginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UIServiceController uiServiceController = new UIServiceController();
    public loginPage() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		uiServiceController.loginPage(out);
		
		out.println("\r\n" + 
				"   <div class=\"signin\"> \r\n" + 
				"\r\n" + 
				"    <div class=\"content\"> \r\n" + 
				"\r\n" + 
				"     <h2>Sign In</h2> \r\n" + 
				"\r\n" + 
				"     <div class=\"form\"><form action=\"handle_Login\"> \r\n" + 
				
				"\r\n" + 
				"      <div class=\"inputBox\"> \r\n" + 
				"\r\n" + 
				"       <input type=\"text\" name=\"userName\" required> <i>Username</i> \r\n" + 
				"\r\n" + 
				"      </div><br> \r\n" + 
				"\r\n" + 
				"      <div class=\"inputBox\"> \r\n" + 
				"\r\n" + 
				"       <input type=\"password\" name=\"passWord\" required> <i>Password</i> \r\n" + 
				"\r\n" + 
				"      </div><br> \r\n" + 
				"\r\n" + 
				"      <div class=\"links\"> <a href=\"#\">Forgot Password</a> <a href=\"#\">Signup</a> \r\n" + 
				"\r\n" + 
				"      </div><br> \r\n" + 
				"\r\n" + 
				"      <div class=\"inputBox\"> \r\n" + 
				"\r\n" + 
				"       <input type=\"submit\" value=\"Login\"> \r\n" + 
				"\r\n" + 
				"      </div></form> \r\n" + 
				"\r\n" + 
				"     </div> \r\n" + 
				"\r\n" + 
				"    </div> \r\n" + 
				"\r\n" + 
				"   </div> \r\n" + 
				"\r\n" + 
				"  </section> <!-- partial --> \r\n" + 
				"\r\n" + 
				" </body>\r\n" + 
				"\r\n" + 
				"</html>");
		
		uiServiceController.cssLogin(out);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
