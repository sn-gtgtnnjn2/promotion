package controller.chara;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class CharacterManageServlet
 */
@WebServlet("/chara/characterManage")
public class CharacterManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CharacterManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		CharasDao cd = DaoFactory.createCharasDao();
		List<CharaInfoBean> charaInfoList = null;
		try {
			
			List<CharaDto> charaList = cd.selectByCreaterId(userId);
			charaInfoList = storeCharaInfo(charaList);
		} catch (SQLException e) {
			System.out.println("キャラクターリストの取得に失敗しました。");
			e.printStackTrace();
		}
		request.setAttribute("charaInfoList", charaInfoList);
		System.out.println(charaInfoList.size());
		request.getRequestDispatcher("/WEB-INF/view/chara/list.jsp").forward(request, response);
	}

	private List<CharaInfoBean> storeCharaInfo(List<CharaDto> charaList) {
		List<CharaInfoBean> charaInfBenList = new ArrayList<CharaInfoBean>();
		for(int i = 0; i < charaList.size(); i++) {
			CharaInfoBean cib = new CharaInfoBean();
			cib.setCharacterName(charaList.get(i).getName());
			cib.setCharacterNameFurigana(charaList.get(i).getNameKana());
			cib.setImageFilePath(charaList.get(i).getImagePath());
			cib.setExternalLink(charaList.get(i).getExternalLink());
			charaInfBenList.add(cib);
		}
		return charaInfBenList;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
