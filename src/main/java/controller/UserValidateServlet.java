package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.PreUserDao;
import dto.PreUser;
/**
 * Servlet implementation class UserValidateServlet
 */
@WebServlet("/validate")
public class UserValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserValidateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメーター取得
		String strId = request.getParameter("id");
		String uuid = request.getParameter("uuid");
		
		Integer id = null;
		List<String> errorList = new ArrayList<String>();
		// バリデーション処理
		try {
			id = Integer.parseInt(strId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		// 認証する
		PreUserDao pud = DaoFactory.createPreUserDao();
		PreUser puOnDb = pud.findById(id);
		
		if(Objects.isNull(puOnDb)) {
			// レコードが見つからないエラー
			errorList.add("不正なURLです");
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
			System.out.println("a");
			return;
		}
		
		// トークン不正
		if(!puOnDb.getToken().equals(uuid)) {
			// トークンが一致しない
			errorList.add("無効なurlです");
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
			System.out.println(puOnDb.getToken());
			System.out.println(uuid);
			System.out.println("b");
			return;
		}		
		
		// 有効期限切れ
		Date currentDate = new Date();
		if(currentDate.after(puOnDb.getExpiresAt())) {
			// レコードが見つからないエラー
			errorList.add("有効期限切れです");
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
			System.out.println("c");
			return;
		}
		
		// 認証が正しいので、email情報のみ引き継いで、本登録画面へ
		request.setAttribute("email", puOnDb.getEmail());
		
		request.getRequestDispatcher("/WEB-INF/view/user_regist.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
