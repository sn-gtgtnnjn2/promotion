package controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.DaoFactory;
import dao.ProfileDao;
import dao.UserDao;
import dto.User;
import util.Base64ImageEncoder;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
//@MultipartConfig(location = "C:/Users/zd2Q17/temp", maxFileSize = 1024 * 1024)
@MultipartConfig(location = "C:/tmp", maxFileSize = 1024 * 1024)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long MAX_FILE_SIZE = 1024 * 500;
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
	       // Check if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Error: Form must has enctype=multipart/form-data.");
            return;
        }
		
		String userId = request.getParameter("userId");
		Integer id = getIdFromUsers(userId);
		Part upfile = request.getPart("profile-image");
		
		if(Objects.isNull(id)) {			
			List<String> errorList = new ArrayList<String>();
			errorList.add("アップロード失敗：ユーザIdが取得できませんでした。");
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
		}

		List<String> errorList = new ArrayList<String>();
		
		// 画像リサイズ用の変数を用意
		BufferedImage resizedImage = null;
		
		// 画像ファイルのバリデーションと、サイズ変更
		try {
			if (!validateInputImage(upfile)) {
				errorList.add("jpg, jpeg, png, gifのいずれかの形式でアップロードしてください。");
				System.out.println("Image validation failed for file: " + upfile.getSubmittedFileName());
				request.getRequestDispatcher("/portal").forward(request, response);
				return;
			} else {
				// リサイズ処理
				resizedImage = resizeImage(upfile);
				System.out.println("resize直後" + Objects.isNull(resizedImage));
				if (Objects.isNull(resizedImage)) {
					errorList.add("画像のリサイズに失敗しました。ファイルサイズは2MB以内としてください。");
					System.out.println("Image resizing failed for file: " + upfile.getSubmittedFileName());
				}
			}
		} catch (IOException e) {
			System.out.println("画像のリサイズに失敗しました");
			e.printStackTrace();
		}
		
		String fileExtention =  getFileExtension(upfile).replace(".", "");
		String filename = upfile.getSubmittedFileName();
		if (filename != null && isImageFile(filename)) {
			try {
				String saveTargetPath = saveFile(resizedImage, filename, id, fileExtention);
				ProfileDao pd = DaoFactory.createProfileDao();
				ServletContext ctx = request.getServletContext();
				String path = ctx.getRealPath(UploadServlet.UPLOAD_DIR);
				File imgFile = new File(path + "/" + saveTargetPath);
				pd.updateImage(userId, saveTargetPath, Base64ImageEncoder.encodeImage(imgFile.getPath()), fileExtention);

			} catch (IOException e) {
				errorList.add("アップロード失敗：画像の保存に失敗しました");
				request.setAttribute("errorList", errorList);
				request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
				e.printStackTrace();
				
			} catch (SQLException e) {
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

//	private String saveFile(Part part, String filename, Integer id) throws IOException {
//		Integer unitDir = id / 100 + 1;
//		File userDir = new File(getServletContext().getRealPath(UPLOAD_DIR), unitDir.toString());
//		System.out.println(userDir);
//		if(!userDir.exists()) {
//			userDir.mkdir();
//		}
//		try {
//			File file = new File(userDir, id + "_" + filename);
//			System.out.println("saveFile書き込み先" + file.getAbsolutePath());
//			part.write(file.getAbsolutePath());	
//		} catch(IOException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		return unitDir.toString() + "/" + id + "_" + filename;
//	}
	
	private String saveFile(BufferedImage image, String filename, Integer id, String extention) throws IOException {
	    System.out.println("bufferedimage:" + Objects.isNull(image));
		Integer unitDir = id / 100 + 1;
		File uploadDir = new File(getServletContext().getRealPath(UPLOAD_DIR));
	    File userDir = new File(getServletContext().getRealPath(UPLOAD_DIR), unitDir.toString());
	    System.out.println(userDir);
	    if (!uploadDir.exists()) {
	    	uploadDir.mkdir();
	    }
	    if (!userDir.exists()) {
	        userDir.mkdir();
	    }
	    try {
	        File file = new File(userDir, id + "_" + filename);
	        System.out.println("saveFile書き込み先" + file.getAbsolutePath());
	        ImageIO.write(image, extention , file);  // 画像をPNG形式で保存
	        System.out.println("aa");
	    } catch (IOException e) {
	    	System.out.println("bb");
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

    private boolean validateInputImage(Part filePart) throws IOException {
        // 拡張子のチェック
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            return false;
        }
        return true;
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
    
    private String getFileExtension(Part filePart) {
        String contentDisposition = filePart.getHeader("content-disposition");
        for (String part : contentDisposition.split(";")) {
            if (part.trim().startsWith("filename")) {
                return part.substring(part.lastIndexOf('.')).replace("\"", "");
            }
        }
        return "";
    }
}
