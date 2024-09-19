package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.DaoFactory;
import dao.ProfileDao;
import dao.UserDao;
import dto.User;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
@MultipartConfig(location = "C:/Users/zd2Q17/temp", maxFileSize = 1024 * 1024)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static String UPLOAD_DIR="/WEB-INF/uploads";
    public String[] IMAGE_FILE_EXTENTIONS = {".jpg", ".jpeg", ".png", ".gif"};
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errorList = new ArrayList<String>();
		errorList.add("アップロード失敗：画像が指定されていません");
		request.setAttribute("errorList", errorList);
		request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		Integer id = getIdFromUsers(userId);
		Part upfile = request.getPart("profile-image");
		
		if(Objects.isNull(id)) {			
			List<String> errorList = new ArrayList<String>();
			errorList.add("アップロード失敗：ユーザIdが取得できませんでした。");
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
		}

		String filename = upfile.getSubmittedFileName();
		if (filename != null && isImageFile(filename)) {
			try {
				String saveTargetPath = saveFile(upfile, filename, id);
				ProfileDao pd = DaoFactory.createProfileDao();
				pd.updateImagePath(userId, saveTargetPath);

			} catch (IOException e) {
				List<String> errorList = new ArrayList<String>();
				errorList.add("アップロード失敗：画像の保存に失敗しました");
				request.setAttribute("errorList", errorList);
				request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
				e.printStackTrace();
				System.out.println("filesave失敗" + filename);
				
			} catch (SQLException e) {
				List<String> errorList = new ArrayList<String>();
				errorList.add("アップロード失敗：画像の保存が登録できませんでした");
				request.setAttribute("errorList", errorList);
				request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
				e.printStackTrace();
				System.out.println("filesave失敗" + filename);
			}
		}

		System.out.println("filenameは" + upfile.getSubmittedFileName());
		
		// 過去ファイルの削除処理
		// todo
		
		// 
		request.getRequestDispatcher("portal").forward(request, response);
	}

	private Integer getIdFromUsers(String userId) {
		UserDao ud = DaoFactory.createUserDao();
		User user = ud.findByUserId(userId);
		return user.getId();
	}

	private String saveFile(Part part, String filename, Integer id) throws IOException {
		Integer unitDir = id / 100 + 1;
		File userDir = new File(getServletContext().getRealPath(UPLOAD_DIR), unitDir.toString());
		System.out.println(userDir);
		if(!userDir.exists()) {
			userDir.mkdir();
		}
		try {
			File file = new File(userDir, id + "_" + filename);
			System.out.println("saveFile書き込み先" + file.getAbsolutePath());
			part.write(file.getAbsolutePath());	
		} catch(IOException e) {
			e.printStackTrace();
			throw e;
		}
		return unitDir.toString() + "/" + id + "_" + filename;
	}

	private boolean isImageFile(String filename) {
		String lowerCaseFileName = filename.toLowerCase();
		boolean ret = false;
		for (int i = 0; i < IMAGE_FILE_EXTENTIONS.length; i++) {
			System.out.println(IMAGE_FILE_EXTENTIONS[i]);
			System.out.println("期待値：" + lowerCaseFileName);
			if (lowerCaseFileName.endsWith(IMAGE_FILE_EXTENTIONS[i])) {
				ret = true;
			}
		}
		System.out.println("isImageFile:"+ret);
		return ret;
	}

}
