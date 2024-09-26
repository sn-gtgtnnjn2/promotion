package controller.chara;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.CharaInfoBean;
import util.StringValidator;

/**
 * Servlet implementation class AddCharacterServlet
 */
@WebServlet("/chara/addCharacter")
@MultipartConfig(location = "C:/Users/zd2Q17/temp", maxFileSize = 1024 * 1024)
public class AddCharacterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
    private static final String TMP_UPLOAD_DIR = "tmp_chara_imgs";

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
		HttpSession session = request.getSession();
		CharaInfoBean charaInfo =  (CharaInfoBean) session.getAttribute("charaInfo");
		if(!Objects.isNull(charaInfo)) {
			request.setAttribute("charaInfo", charaInfo);
		}
		request.getRequestDispatcher("/WEB-INF/view/chara/regist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		String characterName = request.getParameter("characterName");
		String characterNameFurigana = request.getParameter("characterNameFurigana");
		String memo = request.getParameter("memo");
		String externalLink = request.getParameter("externalLink");
		
		// 画像リサイズ用の変数を用意
		Part filePart = request.getPart("imageUpload");
		BufferedImage resizedImage = null;
		String tmpImageFilePath = null;
		String imageFileName = null;
		
		// 画面返却用のBeanを用意
		CharaInfoBean charaInfo = new CharaInfoBean();
		
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
				System.out.println("Image validation failed for file: " + filePart.getSubmittedFileName());
			} else {
	            // リサイズ処理
	            resizedImage = resizeImage(filePart);
	            if(Objects.isNull(resizedImage)) {
	            	errorList.add("画像のリサイズに失敗しました。ファイルサイズは2MB以内としてください。");
	            	System.out.println("Image resizing failed for file: " + filePart.getSubmittedFileName());
	            } else {
	                // ファイル名の生成
	            	String fileExtention =  getFileExtension(filePart).replace(".", "");
	            	imageFileName = UUID.randomUUID().toString() + "_" +
	                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "." + fileExtention;
	                System.out.println("filename:" + imageFileName);
	            	tmpImageFilePath = saveImage(imageFileName, resizedImage, fileExtention, TMP_UPLOAD_DIR);
	            	System.out.println("Image saved successfully at: " + tmpImageFilePath);
	            }
			}
		} catch (IOException e) {
			e.printStackTrace();
			errorList.add("画像ファイルの読み込みに失敗しました。ファイルサイズは2MB以内としてください。");
			System.out.println("IOException occurred: " + e.getMessage());
		}
		

		if (errorList.size() > 0) {
            // バリデーションNGの場合、入力フォーム画面に戻す
//            request.setAttribute("characterName", characterName);
//            request.setAttribute("characterNameFurigana", characterNameFurigana);
//            request.setAttribute("memo", memo);
//            request.setAttribute("externalLink", externalLink);
//            request.setAttribute("validationError", "入力内容に誤りがあります。再度確認してください。");
//            request.getRequestDispatcher("/WEB-INF/view/chara/regist.jsp").forward(request, response);
			charaInfo.setCharacterName(characterName);
			charaInfo.setCharacterNameFurigana(characterNameFurigana);
			charaInfo.setMemo(memo);
			charaInfo.setExternalLink(externalLink);
			charaInfo.setImageFileName(imageFileName);
			request.setAttribute("charaInfo", charaInfo);
			request.getRequestDispatcher("/WEB-INF/view/chara/regist.jsp").forward(request, response);
        } else {
            // バリデーションOKの場合、確認画面に遷移
//            request.setAttribute("characterName", characterName);
//            request.setAttribute("characterNameFurigana", characterNameFurigana);
//            request.setAttribute("memo", memo);
//            request.setAttribute("externalLink", externalLink);
//            request.setAttribute("tmpImageFilePath", tmpImageFilePath);
//            request.setAttribute("imageFileName", imageFileName);
//            request.getRequestDispatcher("/WEB-INF/view/chara/confirm.jsp").forward(request, response);
			charaInfo.setCharacterName(characterName);
			charaInfo.setCharacterNameFurigana(characterNameFurigana);
			charaInfo.setMemo(memo);
			charaInfo.setExternalLink(externalLink);
			charaInfo.setImageFileName(imageFileName);
			charaInfo.setTmpImageFilePath(tmpImageFilePath);
			HttpSession session = request.getSession();
			session.setAttribute("charaInfo", charaInfo);
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
        	return false;
        }

        return true;
    }

    private String getFileExtension(Part filePart) {
        String contentDisposition = filePart.getHeader("content-disposition");
        for (String part : contentDisposition.split(";")) {
            if (part.trim().startsWith("filename")) {
                return part.substring(part.lastIndexOf('.')).replace("\"", "");
            }
        }
        return "";
    }
    
    private BufferedImage resizeImage(Part filePart) throws IOException {
        BufferedImage originalImage;
        try (InputStream inputStream = filePart.getInputStream()) {
            originalImage = ImageIO.read(inputStream);
        }

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
//    
//    public static String saveBufferedImageToTemp(BufferedImage image, String formatName) throws IOException {
//        // 一時ファイルを作成
//        File tempFile = File.createTempFile("tempImage", "." + formatName);
//        
//        // BufferedImageを一時ファイルに書き込む
//        ImageIO.write(image, formatName, tempFile);
//        
//        // 一時ファイルのパスを出力
//        System.out.println("Image saved to: " + tempFile.getAbsolutePath());
//        
//        return tempFile.getAbsolutePath();
//    }
//    
    private String saveImage(String fileName, BufferedImage image, String fileExtension, String location) throws IOException {
        // アップロードパスの生成
        String uploadPath = getServletContext().getRealPath("") + File.separator + location;
        File uploadDir = new File(uploadPath);
        System.out.println("uploadDir:" + uploadDir.getAbsolutePath());

        // ディレクトリが存在しない場合は作成
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
        	System.out.println("ディレクトリが存在しない");
            throw new IOException("Failed to create directory: " + uploadPath);
        }

        // 画像の保存
        //File outputFile = new File(uploadPath, fileName);
        File outputFile = new File(uploadDir.getPath(), fileName);
        System.out.println("outputFile_abs:" + outputFile.getAbsolutePath());
        if(!ImageIO.write(image, fileExtension, outputFile)) {
        	System.out.println("書き込みに失敗");
        }

        // サーブレットコンテキストパスからの相対パスを生成
        String relativePath = "/" + location + "/" + fileName;
        System.out.println("relativePath:" + relativePath);

        return relativePath;
    }
}
