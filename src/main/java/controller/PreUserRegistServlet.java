package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.PreUserDao;
import dto.PreUser;
import util.MailSender;

/**
 * Servlet implementation class PreUserRegistServlet
 */
@WebServlet("/preUserRegist")
public class PreUserRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreUserRegistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/pre_user_regist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("mail");
		
		// todo 既登録チェック
		
		// @todo メールのバリデーション機能
		boolean isValid = true;
		if(!isValid) {
			// 入力されたメールアドレスをセット
			request.setAttribute("email", email);
			// メール入力画面に返す
			request.getRequestDispatcher("/WEB-INF/view/pre_user_regist.jsp").forward(request, response);
			return;
		}
		
		// トークン生成
		String uuid = UUID.randomUUID().toString();
		
		// 認証期限の設定
		LocalDateTime ldt = LocalDateTime.now();
		LocalDateTime expiresAt = ldt.plusMinutes(30);
		
		// データ登録
		PreUser pu = new PreUser(null, email, uuid, false, Timestamp.valueOf(expiresAt), null, null, null);
		PreUserDao pud = DaoFactory.createPreUserDao();
		pud.insert(pu);
		
		// 登録後データ参照
		PreUser updatedPu = pud.findByEmailAndToken(email, uuid);
		System.out.println(updatedPu.getId());
		
		// String url = "http://localhost:8080/kadai2/validate?id=" + updatedPu.getId() + "&uuid=" + uuid ;
		String url = "http://localhost:8080/SEISAKU_KADAI/validate?id=" + updatedPu.getId() + "&uuid=" + uuid ;
		
		request.setAttribute("email", email);
		request.setAttribute("uuid", uuid);
		request.setAttribute("url", url);
		request.setAttribute("expiresAt", expiresAt);
		MailSender.sendMessege(url);
		
		request.getRequestDispatcher("/WEB-INF/view/pre_user_regist_done.jsp").forward(request, response);
		return;
	}

}
