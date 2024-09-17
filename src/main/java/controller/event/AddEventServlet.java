package controller.event;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.EventAndDetail;
import util.DateValidator;
import util.GeneralFormatter;
import util.StringValidator;

/**
 * Servlet implementation class AddEventServlet
 */
@WebServlet("/event/addEvent")
public class AddEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/event/regist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッション情報からユーザーIdを取得する
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		
		String eventTitle = request.getParameter("eventName");
		String strEventDate = request.getParameter("eventDate");
		String scenarioTitle = request.getParameter("scenarioName");
		String details = request.getParameter("details");
		String strNumberOfParticipants = request.getParameter("numberOfParticipants");
		
		// エラーリスト
		List<String> errorList = new ArrayList<String>();
		StringValidator sv = new StringValidator();
		DateValidator dv = new DateValidator();
		
		// 文字種変換
		Date eventDate = null;
		Integer numberOfParticipants = null;
		try {
			System.out.println("L67:" + strEventDate);
			eventDate = GeneralFormatter.convDtLocalToDate(strEventDate);
			numberOfParticipants = Integer.parseInt(strNumberOfParticipants);
			
		} catch (NumberFormatException e) {
			errorList.add(String.format("参加人数は数字で入力してください"));
			e.printStackTrace();
		} catch(ParseException e) {
			errorList.add(String.format("入力されたイベント日時が不正です"));
			e.printStackTrace();
		}
		
		if(errorList.size() > 0) {
			request.setAttribute("eventName", eventTitle);
			request.setAttribute("eventDate", eventDate);
			request.setAttribute("scenarioName", scenarioTitle);
			request.setAttribute("details", details);
			request.getRequestDispatcher("/WEB-INF/view/event/regist.jsp").forward(request, response);
			return;
		}
		
		// バリデーション
		// 必須チェック 文字種チェック　長さチェック
		if(!sv.validate(eventTitle, 1, 40, StringValidator.NO_CHECK, "イベント名")) {
			errorList.addAll(sv.getErrorList());
		}
		sv.clearErrorMessage();
		
		if(!dv.validate(eventDate, "イベント日時")) {
			errorList.addAll(dv.getErrorList());
		}
		dv.clearErrorMessage();
		
		if(!sv.validate(scenarioTitle, 1, 45, StringValidator.NO_CHECK, "シナリオ名")) {
			errorList.addAll(sv.getErrorList());
		}
		sv.clearErrorMessage();
		
		if(!sv.validate(details, 0, 1200, StringValidator.NO_CHECK, "イベント詳細")) {
			errorList.addAll(sv.getErrorList());
		}
		sv.clearErrorMessage();
		
		if(numberOfParticipants < 1 || numberOfParticipants > 100) {
			errorList.add("参加人数:1～100の範囲で入力してください");
		}
		
		
		// 返却用データ登録
		request.setAttribute("eventName", eventTitle);
		request.setAttribute("eventDate", eventDate);
		request.setAttribute("scenarioName", scenarioTitle);
		request.setAttribute("details", details);
		request.setAttribute("numberOfParticipants", numberOfParticipants);
		
		// 一個以上エラーがあれば、ユーザー情報登録画面へ返却
		if(errorList.size() > 0) {
System.out.println("エラーあり");
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/event/regist.jsp").forward(request, response);
			return;
		}
		
		// エラーがなければ、セッションに情報を詰めて、確認画面へ
		EventAndDetail ead = new EventAndDetail(userId, eventTitle, scenarioTitle, details);
		ead.setOrganizerName(userName);
		ead.setOrganizerId(userId);
		session.setAttribute("ead", ead);
		request.getRequestDispatcher("/WEB-INF/view/event/confirm.jsp").forward(request, response);
		
		
	}

}
