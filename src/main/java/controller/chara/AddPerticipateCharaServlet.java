package controller.chara;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.DaoFactory;
import dao.ScenarioEntriedCharaDao;
import dto.ScenarioEntriedCharaDto;

/**
 * Servlet implementation class AddPerticipateCharaServlet
 */
@WebServlet("/AddPerticipateCharaServlet")
public class AddPerticipateCharaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPerticipateCharaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getClass().getName());

	        BufferedReader reader = request.getReader();
	        Gson gson = new Gson();
	        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

	        String characterIdStr = jsonObject.get("characterId").getAsString();
	       // String characterName = jsonObject.get("characterName").getAsString();
	        String eventIdStr = jsonObject.get("eventId").getAsString();
		
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		//String characterName = request.getParameter("characterName");
		//String characterIdStr = request.getParameter("characterId");
		//String eventIdStr = request.getParameter("eventId");
		System.out.println("characterId->" + characterIdStr);
		System.out.println("eventIdStr->" + eventIdStr);
		
		Integer characterId = null;
		Integer eventId = null;
		try {
			characterId = Integer.parseInt(characterIdStr);
			eventId = Integer.parseInt(eventIdStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println(getClass().getName() + "数値変換に失敗");
		}
		
		if(Objects.isNull(eventId) || Objects.isNull(characterId)) {
			
		}
		
		ScenarioEntriedCharaDto secDto = new ScenarioEntriedCharaDto();
		secDto.setCharacterId(characterId);
		secDto.setEventId(eventId);
		secDto.setPlayerId(userId);
		ScenarioEntriedCharaDao secd = DaoFactory.createScenarioEntriedCharaDao();
		try {
			secd.insert(secDto);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("dao処理で失敗");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
