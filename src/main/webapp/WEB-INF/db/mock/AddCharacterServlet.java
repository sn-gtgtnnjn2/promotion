package controller.chara;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import util.StringValidator;

/**
 * Servlet implementation class AddCharacterServlet
 */
@WebServlet("/chara/addCharacter")
@MultipartConfig
public class AddCharacterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCharacterServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String characterName = request.getParameter("characterName");
		String characterNameFurigana = request.getParameter("characterNameFurigana");
		String memo = request.getParameter("memo");
		String externalLink = request.getParameter("externalLink");

		Part filePart = request.getPart("imageUpload");
		
		// エラーリスト
		List<String> errorList = new ArrayList<String>();
		StringValidator sv = new StringValidator();

		// バリデーション
		sv.clearErrorMessage();
		if(!sv.validate(characterName, 1, 40, StringValidator.NO_CHECK, "キャラクター名")) {
			errorList.addAll(sv.getErrorList());
		}
		
		sv.clearErrorMessage();
		if(!sv.validate(characterName, 1, 40, StringValidator.FULL_KATAKANA_ONLY, "キャラクター名フリガナ")) {
			errorList.addAll(sv.getErrorList());
		}
		
		sv.clearErrorMessage();
		if(!sv.validate(characterName, 1, 40, StringValidator.NO_CHECK, "メモ")) {
			errorList.addAll(sv.getErrorList());
		}
		
		sv.clearErrorMessage();
		if(!sv.validate(characterName, 1, 1300, StringValidator.HALF_ALF_NUM_SBL, "外部リンク")) {
			errorList.addAll(sv.getErrorList());
		}
		
		// 画像ファイルのバリデーションと、サイズ変更
		try {
			if (!validateInputImage(filePart)) {
				errorList.add("jpg, jpeg, png, gifのいずれかの形式でアップロードしてください。");
			}
		} catch (IOException e) {
			errorList.add("画像ファイルの読み込みに失敗しました。ファイルサイズは2MB以内としてください。");
		}
		

		if (errorList.size() > 0) {
            // バリデーションNGの場合、入力フォーム画面に戻す
            request.setAttribute("characterName", characterName);
            request.setAttribute("characterNameFurigana", characterNameFurigana);
            request.setAttribute("memo", memo);
            request.setAttribute("externalLink", externalLink);
            request.setAttribute("validationError", "入力内容に誤りがあります。再度確認してください。");
            request.getRequestDispatcher("/WEB-INF/view/chara/regist.jsp").forward(request, response);
        } else {
            // バリデーションOKの場合、確認画面に遷移
            request.setAttribute("characterName", characterName);
            request.setAttribute("characterNameFurigana", characterNameFurigana);
            request.setAttribute("memo", memo);
            request.setAttribute("externalLink", externalLink);
            request.setAttribute("fileName", filePart.getSubmittedFileName());
            request.getRequestDispatcher("/WEB-INF/view/chara/confirm.jsp").forward(request, response);
        }
	}

    private boolean validateInputImage(Part filePart) throws IOException {
        // 拡張子のチェック
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            return false;
        }

        // ファイルサイズのチェック
        if (filePart.getSize() > MAX_FILE_SIZE) {
            // リサイズ処理
            BufferedImage resizedImage = resizeImage(filePart);
            if (resizedImage == null) {
                return false;
            }
        }

        return true;
    }
    
//    private String saveImage(Part filePart) throws IOException {
//        String fileName = UUID.randomUUID().toString() + "_" + 
//                          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + 
//                          getFileExtension(filePart);
//        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) uploadDir.mkdir();
//
//        BufferedImage resizedImage = resizeImage(filePart);
//        if (resizedImage != null) {
//            ImageIO.write(resizedImage, getFileExtension(filePart).substring(1), new File(uploadPath + File.separator + fileName));
//        } else {
//            filePart.write(uploadPath + File.separator + fileName);
//        }
//
//        return UPLOAD_TMP_DIR + "/" + fileName;
//    }
//
//    private String getFileExtension(Part filePart) {
//        String contentDisposition = filePart.getHeader("content-disposition");
//        for (String part : contentDisposition.split(";")) {
//            if (part.trim().startsWith("filename")) {
//                return part.substring(part.lastIndexOf('.')).replace("\"", "");
//            }
//        }
//        return "";
//    }
    
    private BufferedImage resizeImage(Part filePart) throws IOException {
        BufferedImage originalImage = ImageIO.read(filePart.getInputStream());
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        int targetWidth = originalWidth;
        int targetHeight = originalHeight;

        // リサイズが必要な場合
        if (filePart.getSize() > MAX_FILE_SIZE) {
            double aspectRatio = (double) originalHeight / originalWidth;
            targetWidth = (int) Math.sqrt(MAX_FILE_SIZE / aspectRatio);
            targetHeight = (int) (targetWidth * aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return resizedImage;
    }
}
