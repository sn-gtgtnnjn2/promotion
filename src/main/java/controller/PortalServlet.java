package controller;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoFactory;
import dao.ProfileDao;
import dto.Profile;
import util.Base64ImageEncoder;

/**
 * Servlet implementation class PortalServlet
 */
@WebServlet("/portal")
public class PortalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		if(Objects.isNull(userId)) {
			// 認証されていない可能性があるので、ログインページに遷移
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
		// プロフィール情報を取得
		ProfileDao pd = DaoFactory.createProfileDao();
		Profile profile = pd.findByUserId(userId);
		System.out.println("imagepath:" + profile.getImagePath());
		
		// プロフィール画像をエンコード
		ServletContext ctx = request.getServletContext();
		String path = ctx.getRealPath(UploadServlet.UPLOAD_DIR);
		File imgFile = new File(path + "/" +profile.getImagePath());
		System.out.println("ある？"+imgFile.exists());
		String strImageData = "";
		if(imgFile.exists()) {
			strImageData = Base64ImageEncoder.encodeImage(imgFile.getPath());
		}
		
		request.setAttribute("strImageData", strImageData);
		request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
