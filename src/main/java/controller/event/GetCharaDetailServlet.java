package controller.event;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.CharasDao;
import dao.DaoFactory;
import dto.CharaDto;

/**
 * Servlet implementation class GetCharaDetailServlet
 */
@WebServlet("/event/GetCharaDetailServlet")
public class GetCharaDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCharaDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String characterIdStr = request.getParameter("characterId");
		System.out.println("characterIdStr" + characterIdStr);
		
		// 数字を数値に変換
		Integer characterId = null;
		try{
			characterId = Integer.parseInt(characterIdStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println(getClass().getName() + "データの取得に失敗しました");
		}
		
        // キャラクターの詳細情報をデータベースから取得する
		CharasDao cd = DaoFactory.createCharasDao();
		CharaDto charaInfo = null;
		try {
			charaInfo = cd.findByCharacterId(characterId);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("charInfo->" + charaInfo);
		System.out.println("charInfo.name->" + charaInfo.getName());
		
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(charaInfo));
        out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
