package controller.chara;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddCharacterServlet
 */
@WebServlet("chara/completeRegistration")
public class RegistCharacterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String TMP_UPLOAD_DIR = "tmp_chara_imgs";
	//private static final String UPLOAD_DIR = "chara_imgs";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistCharacterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/chara/regist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String characterName = request.getParameter("characterName");
//		String characterNameFurigana = request.getParameter("characterNameFurigana");
//		String memo = request.getParameter("memo");
//		String externalLink = request.getParameter("externalLink");
//		String tmpImageFilePath = request.getParameter("tmpImageFilePath");
//		String imageFileName = request.getParameter("imageFileName");
//		// 画像の保存
//		//File tmpImgFile = new File (getServletContext().getRealPath("") + File.separator + tmpImageFilePath);
//		System.out.println("source："+getServletContext().getRealPath("") + File.separator + tmpImageFilePath);
//		Path source = Paths.get(getServletContext().getRealPath("") + File.separator + tmpImageFilePath);
//	    Path target = Paths.get(getServletContext().getRealPath("") + File.separator + UPLOAD_DIR + imageFileName);
//		
//        try {
//            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	    
//		request.setAttribute("characterName", characterName);
//		request.setAttribute("characterNameFurigana", characterNameFurigana);
//		request.setAttribute("memo", memo);
//		request.setAttribute("externalLink", externalLink);
//
//
//		request.getRequestDispatcher("/WEB-INF/view/chara/done.jsp").forward(request, response);
//	}

//	private String saveImage(Part filePart) throws IOException {
//		String fileName = UUID.randomUUID().toString() + "_"
//				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
//				+ getFileExtension(filePart);
//		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
//		File uploadDir = new File(uploadPath);
//		if (!uploadDir.exists())
//			uploadDir.mkdir();
//
//		filePart.write(uploadPath + File.separator + fileName);
//		return UPLOAD_DIR + "/" + fileName;
//	}

//	private String getFileExtension(Part filePart) {
//		String contentDisposition = filePart.getHeader("content-disposition");
//		for (String part : contentDisposition.split(";")) {
//			if (part.trim().startsWith("filename")) {
//				return part.substring(part.lastIndexOf('.')).replace("\"", "");
//			}
//		}
//		return "";
//	}
}
