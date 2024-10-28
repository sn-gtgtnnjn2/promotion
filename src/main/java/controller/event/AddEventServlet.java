package controller.event;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EventAndDetailBean;
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
		HttpSession session = request.getSession();
		EventAndDetailBean eadb =  (EventAndDetailBean) session.getAttribute("eadb");
		if(!Objects.isNull(eadb)) {
			System.out.println("戻った値" + GeneralFormatter.toISO8601(eadb.getEventDate()));
			request.setAttribute("eventDate", GeneralFormatter.toISO8601(eadb.getEventDate()));
			request.setAttribute("recruitmentStartDate",  GeneralFormatter.toISO8601(eadb.getRecruitmentStartDate()));
			request.setAttribute("recruitmentEndDate",  GeneralFormatter.toISO8601(eadb.getRecruitmentEndDate()));
			request.setAttribute("eadb", eadb);			
		}
		request.getRequestDispatcher("/WEB-INF/view/event/regist.jsp").forward(request, response);
		System.out.println("doGetです終わり");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッション情報からユーザーIdを取得する
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		
		String eventTitle = request.getParameter("eventTitle");
		String strEventDate = request.getParameter("eventDate");
		String strEventTime = request.getParameter("eventTime");
		String strRecruitmentStartDate = request.getParameter("recruitmentStartDate");
		String strRecruitmentStartTime = request.getParameter("recruitmentStartTime");
		String strRecruitmentEndDate = request.getParameter("recruitmentEndDate");
		String strRecruitmentEndTime = request.getParameter("recruitmentEndTime");
		String scenarioTitle = request.getParameter("scenarioTitle");
		String detail = request.getParameter("detail");
		System.out.println(detail);
		String strmemberLimit = request.getParameter("memberLimit");
		String stropenLevel = request.getParameter("openLevel");
		
		// エラーリスト
		List<String> errorList = new ArrayList<String>();
		StringValidator sv = new StringValidator();
		DateValidator dv = new DateValidator();
		
		// 文字種変換
		Date eventDate = null;
		Date recruitmentStartDate = null;
		Date recruitmentEndDate = null;
		Integer memberLimit = null;
		Integer openLevel = null;
		try {
			eventDate = GeneralFormatter.parseCustomDate(strEventDate + " " + strEventTime);
		} catch(ParseException e) {
			errorList.add(String.format("入力されたイベント日時が不正です"));
			e.printStackTrace();
		}
		
		try {
			recruitmentStartDate = GeneralFormatter.parseCustomDate(strRecruitmentStartDate + " " + strRecruitmentStartTime);
			recruitmentEndDate = GeneralFormatter.parseCustomDate(strRecruitmentEndDate + " " + strRecruitmentEndTime);
		} catch(ParseException e) {
			errorList.add(String.format("入力された募集期間が不正です"));
			e.printStackTrace();
		}
		
		try {
			memberLimit = Integer.parseInt(strmemberLimit);
		} catch (NumberFormatException e) {
			errorList.add(String.format("参加人数は数字で入力してください"));
			e.printStackTrace();
		}
		
		try {
			openLevel = Integer.parseInt(stropenLevel);
		} catch (NumberFormatException e) {
			errorList.add(String.format("公開範囲の値が不正です"));
			e.printStackTrace();
		}
		
		if(errorList.size() > 0) {
			EventAndDetailBean eadb = new EventAndDetailBean();
			eadb.setEventTitle(eventTitle);
			eadb.setUserId(userId);
			eadb.setOrganizerName(userName);
			eadb.setOrganizerId(userId);
			eadb.setScenarioTitle(scenarioTitle);
			eadb.setDetail(detail);
			eadb.setRecruitmentStartDate(recruitmentStartDate);
			eadb.setRecruitmentEndDate(recruitmentEndDate);
			eadb.setMemberLimit(memberLimit);
			eadb.setopenLevel(openLevel);
			eadb.setStatus(null);
			
			request.setAttribute("eventDate", GeneralFormatter.toISO8601(eventDate));
			request.setAttribute("recruitmentStartDate",  GeneralFormatter.toISO8601(recruitmentStartDate));
			request.setAttribute("recruitmentEndDate",  GeneralFormatter.toISO8601(recruitmentEndDate));
			session.setAttribute("eadb", eadb);
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
		
		if(!sv.validate(detail, 0, 1200, StringValidator.NO_CHECK, "イベント詳細")) {
			errorList.addAll(sv.getErrorList());
		}
		sv.clearErrorMessage();
		
		if(memberLimit < 1 || memberLimit > 100) {
			errorList.add("参加人数:1～100の範囲で入力してください");
		}
		
		if(!dv.validate(recruitmentStartDate, "募集開始日時")) {
			errorList.addAll(dv.getErrorList());
		}
		dv.clearErrorMessage();
		
		if(!dv.validate(recruitmentEndDate, "募集終了日時")) {
			errorList.addAll(dv.getErrorList());
		}
		dv.clearErrorMessage();
		
		// 一個以上エラーがあれば、ユーザー情報登録画面へ返却
		if(errorList.size() > 0) {
			EventAndDetailBean eadb = new EventAndDetailBean();
			eadb.setEventTitle(eventTitle);
			eadb.setUserId(userId);
			eadb.setOrganizerName(userName);
			eadb.setOrganizerId(userId);
			eadb.setScenarioTitle(scenarioTitle);
			eadb.setDetail(detail);
			eadb.setRecruitmentStartDate(recruitmentStartDate);
			eadb.setRecruitmentEndDate(recruitmentEndDate);
			eadb.setMemberLimit(memberLimit);
			eadb.setopenLevel(openLevel);
			eadb.setStatus(null);
			
			request.setAttribute("eventDate", eventDate);
			request.setAttribute("recruitmentStartDate",  recruitmentStartDate);
			request.setAttribute("recruitmentEndDate",  recruitmentEndDate);
			
			request.setAttribute("eadb", eadb);
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/WEB-INF/view/event/regist.jsp").forward(request, response);
			return;
		}
		
		// エラーがなければ、セッションに情報を詰めて、確認画面へ
		EventAndDetailBean eadb = new EventAndDetailBean();
		eadb.setEventTitle(eventTitle);
		eadb.setEventDate(eventDate);
		eadb.setUserId(userId);
		eadb.setOrganizerName(userName);
		eadb.setOrganizerId(userId);
		eadb.setScenarioTitle(scenarioTitle);
		eadb.setDetail(detail);
		eadb.setRecruitmentStartDate(recruitmentStartDate);
		eadb.setRecruitmentEndDate(recruitmentEndDate);
		eadb.setMemberLimit(memberLimit);
		eadb.setopenLevel(openLevel);
		eadb.setStatus(null);
		
		request.setAttribute("eventDate", GeneralFormatter.toUsualString(eventDate));
		request.setAttribute("recruitmentStartDate",  GeneralFormatter.toUsualString(recruitmentStartDate));
		request.setAttribute("recruitmentEndDate",  GeneralFormatter.toUsualString(recruitmentEndDate));
				
		session.setAttribute("eadb", eadb);
		request.getRequestDispatcher("/WEB-INF/view/event/confirm.jsp").forward(request, response);
		
		
	}

}
