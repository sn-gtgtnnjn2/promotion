package controller.chara;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CharaInfoBean;
import dao.CharasDao;
import dao.DaoFactory;
import dto.CharaDto;

/**
 * Servlet implementation class AddCharacterServlet
 */
@WebServlet("/chara/completeRegistration")
public class RegistCharacterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private static final String TMP_UPLOAD_DIR = "tmp_chara_imgs";
	private static final String UPLOAD_DIR = "chara_imgs";

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータ取得
		HttpSession session = request.getSession();
		CharaInfoBean charaInfo = (CharaInfoBean) session.getAttribute("charaInfo");
		
		// セッションに格納されているはずのキャラクター情報がnullの場合エラー
		List<String> errorList = new ArrayList<String>();
		if(Objects.isNull(charaInfo)) {
			errorList.add("セッション情報の取得に失敗しました。");
			request.getRequestDispatcher("/WEB-INF/view/chara/regist.jsp").forward(request, response);
		}
		String characterName = charaInfo.getCharacterName();
		String characterNameFurigana = charaInfo.getCharacterNameFurigana();
		String memo = charaInfo.getMemo();
		String externalLink = charaInfo.getExternalLink();
		String tmpImageFilePath = charaInfo.getTmpImageFilePath();
		String imageFileName = charaInfo.getImageFileName();
		
		// 画像の保存
		ServletContext context = getServletContext();
		File imageDir = new File(context.getRealPath("") + File.separator + UPLOAD_DIR);

		// ディレクトリが存在しない場合に作成
		if (!imageDir.exists()) {
			if (imageDir.mkdirs()) {
				System.out.println("ディレクトリが作成されました: " + context.getRealPath("") + File.separator + UPLOAD_DIR);
			} else {
				System.out.println("ディレクトリの作成に失敗しました: " + context.getRealPath("") + File.separator + UPLOAD_DIR);
			}
		}

		// 一時保存フォルダから正式なアップロードフォルダへコピー
		System.out.println("source：" + context.getRealPath(tmpImageFilePath));
		Path svContext = Paths.get(context.getRealPath(""));
		Path source = Paths.get(context.getRealPath(tmpImageFilePath));
		Path target = Paths.get(context.getRealPath("") + File.separator + UPLOAD_DIR + File.separator + imageFileName);
		try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			errorList.add("画像ファイルの登録に失敗しました");
			request.setAttribute("charaInfo", charaInfo);
			request.getRequestDispatcher("/WEB-INF/view/chara/confirm.jsp").forward(request, response);
		}
		
		// DB登録処理
		CharasDao cdo = DaoFactory.createCharasDao();
		CharaDto cDto = new CharaDto();
		String userId = (String) session.getAttribute("userId");
		
		cDto.setCreaterId(userId);
		cDto.setName(characterName);
		cDto.setNameKana(characterNameFurigana);
		cDto.setMemo(memo);
		cDto.setImageFileName(imageFileName);
		cDto.setImagePath(svContext.relativize(target).toString());
		cDto.setExternalLink(externalLink);
		
		try {			
			cdo.insert(cDto);
		}catch(SQLException e) {
			errorList.add("キャラクター情報の登録に失敗しました");
			request.setAttribute("charaInfo", charaInfo);
			request.getRequestDispatcher("/WEB-INF/view/chara/confirm.jsp").forward(request, response);
			return;
		}
		
		session.removeAttribute("charaInfo");
		request.getRequestDispatcher("/chara/characterManage").forward(request, response);
	}

}
