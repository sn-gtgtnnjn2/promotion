package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoFactory;
import dao.UserDao;
import dto.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/index")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		List<String> errorList = new ArrayList<String>();
		
		HttpSession session = request.getSession();

		// 既にセッションが存在する場合は一度破棄する
	    if (session != null) {
	      log("セッション破棄 セッションID=[" + session.getId() + "]");
	      session.invalidate();
	    }
	    
	    try {
	      // セッションを新規で作成する
	      session = request.getSession(true);
	      log("セッション作成 セッションID=[" + session.getId() + "]");
	      request.setCharacterEncoding("UTF-8");
	    } catch (Exception e) {
	      log("セッション作成 失敗");
	      // 例外によりセッションの作成に失敗
	      e.printStackTrace();
	    }
		
		UserDao ud = DaoFactory.createUserDao();
		if(!ud.isAuthenticated(userId, password)){
			errorList.add("ユーザーID、またはパスワードが正しくありません");
			request.setAttribute("userId", userId);
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}

		User user = ud.findByUserId(userId);
		// セッションにユーザー情報を格納する
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("userId", user.getUserId());
        request.getRequestDispatcher("/portal").forward(request, response);
        return;
	}

}
