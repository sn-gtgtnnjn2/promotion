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

import com.google.gson.Gson;

import bean.CharaInfoMin;
import dao.CharasDao;
import dao.DaoFactory;
import dto.CharaDto;

/**
 * Servlet implementation class SearchSuggestionsServlet
 */
@WebServlet("/SearchSuggestionsServlet")
public class SearchSuggestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchSuggestionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("到達");
        String query = request.getParameter("query");
        //String searchStr = request.getParameter("searchString");
        // ここでデータベースから部分一致するキャラクター名を取得する
        List<CharaDto> charaInfo = new ArrayList<>();
        CharasDao cd = DaoFactory.createCharasDao();
        try {
        	charaInfo = cd.selectCharasListByNamePartialMatch(query);
			System.out.println("charaInfosize" + charaInfo.size());
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        
        List<CharaInfoMin> suggestions = new ArrayList<>();
        suggestions = storeCharaInfoMin(charaInfo);
        // ダミーデータ
        // ggestions.add("Mario");
        // suggestions.add("Luigi");
        // suggestions.add("Peach");
        // suggestions.add("Bowser");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(suggestions)); // Gsonライブラリを使用してJSONに変換
        out.close();
	}

	private List<CharaInfoMin> storeCharaInfoMin(List<CharaDto> charaInfo) {
		List<CharaInfoMin> cimList = new ArrayList<CharaInfoMin>();
		for(int i = 0; i < charaInfo.size(); i++) {
			CharaInfoMin cim = new CharaInfoMin(charaInfo.get(i).getCharacterId(), charaInfo.get(i).getName(), charaInfo.get(i).getNameKana());
			cimList.add(cim);
		}
		return cimList;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
