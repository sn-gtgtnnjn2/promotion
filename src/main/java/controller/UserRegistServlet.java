package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import dao.DaoFactory;
import dao.ProfileDao;
import dao.UserDao;
import dto.Profile;
import dto.User;
import util.StringValidator;

/**
 * Servlet implementation class UserRegist
 */
@WebServlet("/userRegist")
public class UserRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		request.setAttribute("email", email);
		request.getRequestDispatcher("/WEB-INF/view/user_regist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメーター受け取り
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String passwordComfirm = request.getParameter("passwordComfirm");
		String email = request.getParameter("email");
		
		// エラー
		List<String> errorList = new ArrayList<String>();
		StringValidator sv = new StringValidator();
		
		// バリデーション
		// 必須チェック 文字種チェック　長さチェック
		if(!sv.validate(userId, 1, 20, StringValidator.HALF_ALF_NUM_SBL, "ユーザーID")) {
			errorList.addAll(sv.getErrorList());
		}
		sv.clearErrorMessage();
		
		if(!sv.validate(userName, 1, 20, StringValidator.HALF_ALF_NUM_SBL, "ユーザー名")) {
			errorList.addAll(sv.getErrorList());
		}
		sv.clearErrorMessage();
		
		if(password.equals(passwordComfirm)) {			
			if(!sv.validate(password, 1, 20, StringValidator.HALF_ALF_NUM_SBL, "パスワード")) {
				errorList.addAll(sv.getErrorList());
			}
		} else {
			errorList.add("パスワード:確認用パスワードと一致しません");
		}
		sv.clearErrorMessage();
		
		// 返却用データ登録
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
		request.setAttribute("email", email);
		// request.setAttribute("password", name);
		// request.setAttribute("passwordComfirm", name);
		
		// 一個以上エラーがあれば、ユーザー情報登録画面へ返却
		if(errorList.size() > 0) {
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/user_regist.jsp").forward(request, response);
			return;
		}
		
		// エラーがなければ、User・Profileテーブルに登録し、その後portalへフォワード
		User user = new User(userId, userName, email, BCrypt.hashpw(password, BCrypt.gensalt()));
		ProfileDao pd = DaoFactory.createProfileDao();
		
		try {
			UserDao ud = DaoFactory.createUserDao();
			ud.insert(user);
			Profile profile = new Profile(userId, null, null, null);
			pd.insert(profile);
		} catch (SQLException e) {
			errorList.add("ユーザー情報の登録に失敗しました");
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/user_regist.jsp").forward(request, response);
			e.printStackTrace();
			return;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("userName", userName);
		request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
	}

}
