package controller.chara;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import bean.CharaInfoForEventDetailBean;
import dao.DaoFactory;
import dao.ScenarioEntriedCharaDao;
import dto.CharasForEventDetailDto;

/**
 * Servlet implementation class UpdateCharaListServlet
 */
@WebServlet("/UpdateCharaListServlet")
public class UpdateCharaListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCharaListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		//List<Character> charaListForScreen = 
		String eventIdStr = request.getParameter("eventId");
		Integer eventId = null;
		try {
			eventId = Integer.parseInt(eventIdStr);
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return;
		}
System.out.println(getClass().getName() + "到達");
		List<CharasForEventDetailDto> charaList = null;
		
		// データベースからキャラクターリストを取得
		ScenarioEntriedCharaDao secd = DaoFactory.createScenarioEntriedCharaDao();
		
		// 参加予定キャラクターを取得
		try {
			charaList = secd.getEventEntryCharas(eventId);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		// キャラクター情報をbeanに格納
		List<CharaInfoForEventDetailBean> charaListForScreen = storeCharaListToBean(charaList, userId);
System.out.println(getClass().getName() + "charaListForScreenの長さ->" + charaListForScreen.size());		
		Gson gson = new Gson();
		String jsonResponse = gson.toJson(charaListForScreen);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(jsonResponse);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private List<CharaInfoForEventDetailBean> storeCharaListToBean(List<CharasForEventDetailDto> charaList, String loginUser) {
		List<CharaInfoForEventDetailBean> newCharaList = new ArrayList<CharaInfoForEventDetailBean>();
		for(int i = 0; i < charaList.size(); i ++) {
			CharaInfoForEventDetailBean bean = new CharaInfoForEventDetailBean();
			bean.setCharacterId(charaList.get(i).getCharacterId());
			bean.setEventId(charaList.get(i).getEventId());
			bean.setPlayerId(charaList.get(i).getPlayerId());
			bean.setPlayerName(charaList.get(i).getPlayerName());
			bean.setName(charaList.get(i).getName());
			bean.setNameKana(charaList.get(i).getNameKana());
			bean.setMemo(charaList.get(i).getMemo());
			bean.setExternalLink(charaList.get(i).getExternalLink());
			bean.setImageFileName(charaList.get(i).getImageFilename());
			bean.setImageFilePath(charaList.get(i).getImagePath());
			if(charaList.get(i).getPlayerId().equals(loginUser)) {
				System.out.println(loginUser +":" + "true");
				System.out.println(charaList.get(i).getPlayerId() +":" + "true");
				bean.setIsLoginUserOwner(true);
			} else {
				bean.setIsLoginUserOwner(false);
			}
			newCharaList.add(bean);
		}
		return newCharaList;
	}
}
