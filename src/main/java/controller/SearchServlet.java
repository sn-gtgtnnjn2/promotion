package controller;

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

import org.apache.commons.lang3.StringUtils;

import bean.EventInfoBean;
import dao.DaoFactory;
import dao.EventInfoDao;
import dto.Event;
import dto.EventAndDetail;
import util.GeneralFormatter;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		//String searchQuery = request.getParameter("searchQuery");
		
        // 検索条件を取得
        String eventTitle = request.getParameter("eventTitle");
        String organizerName = request.getParameter("organizerName");
        String scenarioTitle = request.getParameter("scenarioTitle");
        String strEventDate = request.getParameter("eventDate");
		String strfollowersOnly = request.getParameter("followersOnly");
		System.out.println(eventTitle + ":" + organizerName);
		
		// 適切な型に変換
		Date eventDate = null;
		Boolean followersOnly = false;
		
		// エラーリスト
		List<String> errorList = new ArrayList<String>();
		
		try {
			if(!Objects.isNull(strfollowersOnly)) {
				followersOnly = Boolean.parseBoolean(strfollowersOnly);
			}
		} catch (NumberFormatException e) {
			errorList.add(String.format("検索レベルの値が不正です"));
			e.printStackTrace();
		}
			
		try {
			if(!Objects.isNull(strEventDate) && !strEventDate.isEmpty()) {				
				eventDate = GeneralFormatter.convDtLocalToDate(strEventDate);
			}
		} catch(ParseException e) {
			errorList.add(String.format("入力されたイベント日時が不正です"));
			e.printStackTrace();
		}
        
        // イベント一覧を取得
		EventInfoDao eid = DaoFactory.createEventInfoDao();
		String whereStr = buildWhereStr(eventTitle, organizerName, scenarioTitle, eventDate, followersOnly );
		System.out.println(whereStr);
		List<Event> eventList = eid.selectAvailableEventsWithPict(100, whereStr);
		List<EventInfoBean> eventInfoList = storeEventToBean(eventList, userId);
		System.out.println(eventInfoList.size());
		

        // 検索条件をリクエスト属性に設定（戻るボタン用）
		request.setAttribute("eventTitle", eventTitle);
		request.setAttribute("organizerName", organizerName);
		request.setAttribute("scenarioTitle", scenarioTitle);
		request.setAttribute("eventDate", GeneralFormatter.toISO8601(eventDate));
		request.setAttribute("followersOnly", followersOnly);
		request.setAttribute("eventInfoList", eventInfoList);
		
		// 当画面ID
		request.setAttribute("screenId", NavigationManager.SCREEN_SEARCH);
		
		// クエリパラム
		String searchQuery = getQueryParam(eventTitle, organizerName, scenarioTitle,  eventDate, followersOnly);
				
		System.out.println(searchQuery);
		request.setAttribute("searchQuery", searchQuery);
		
		request.getRequestDispatcher("/WEB-INF/view/search.jsp").forward(request, response);
	}

	private String getQueryParam(String eventTitle, String organizerName, String scenarioTitle, Date eventDate,
			Boolean followersOnly) {
		String query = String.format("eventTitle=%s&organizerName=%s&scenarioTitle=%s&eventDate=%s&followersOnly=%s", 
				GeneralFormatter.changeNullToEmpChar(eventTitle)
				, GeneralFormatter.changeNullToEmpChar(organizerName)
				, GeneralFormatter.changeNullToEmpChar(scenarioTitle)
				, GeneralFormatter.changeNullToEmpChar(GeneralFormatter.toISO8601(eventDate))
				, GeneralFormatter.changeNullToEmpChar(followersOnly.toString()));
		// TODO 自動生成されたメソッド・スタブ
		return query;
	}

	private String buildWhereStr(String eventTitle, String organizerName, String scenarioTitle, Date eventDate,
			Boolean searchLevel) {
		StringBuilder sb = new StringBuilder();
		String whereStr = "WHERE event.recruitment_start_date <= now()"
				+ " AND event.recruitment_end_date > now()"
				+ " AND event.cancel_flg = 0"
				+ " AND event.delete_flg = 0";
		sb.append(whereStr);
		
		// イベントIDの指定があれば追加
		if(!StringUtils.isEmpty(eventTitle)) {
			sb.append(" AND event.event_title like '%" + eventTitle +"%'");
		}
		
		// 主催者名
		if(!StringUtils.isEmpty(organizerName)) {
			sb.append(" AND event.organizer_name like '%" + organizerName +"%'");
		}
		
		// シナリオ名
		if(!StringUtils.isEmpty(scenarioTitle)) {
			sb.append(" AND event.scenario_title like '%" + scenarioTitle +"%'");
		}
		
		// 開催日
		if(!Objects.isNull(eventDate)) {
			sb.append(" AND event.event_datetime > '%" + eventDate +"%'");
		}
		
		// フォロワー内検索 todo
		if(!Objects.isNull(searchLevel) && searchLevel) {
			sb.append(" AND event.open_level > '" + 2 +"'");
		}
		
		System.out.println("whereStr->" + sb.toString());
		return sb.toString();
	}

	private List<EventInfoBean> storeEventToBean(List<Event> eventList, String userId) {
		List<EventInfoBean> eibList = new ArrayList<EventInfoBean>();
		for(int i = 0; i < eventList.size(); i ++) {
			EventInfoBean eib = new EventInfoBean();
			eib.setEventId(eventList.get(i).getEventId());
			eib.setEventTitle(eventList.get(i).getEventTitle());
			eib.setEventDate(eventList.get(i).getEventDate());
			eib.setOrganizerId(eventList.get(i).getOrganizerId());
			eib.setOrganizerName(eventList.get(i).getOrganizerName());
			eib.setOrganizerImageString(eventList.get(i).getOrganizerImageString());
			eib.setOpenLevel(eventList.get(i).getOpenLevel());
			eib.setScenarioTitle(eventList.get(i).getScenarioTitle());
			eib.setRecruitmentStartDate(eventList.get(i).getRecruitmentStartDate());
			eib.setRecruitmentEndDate(eventList.get(i).getRecruitmentEndDate());
			eib.setStatus(EventAndDetail.getIsAvailable(eventList.get(i), eventList.get(i).getCurrentApprovedNum()));
			eib.setCurrentSignUpNum(eventList.get(i).getCurrentSignUpNum());
			eib.setCurrentApprovedNum(eventList.get(i).getCurrentApprovedNum());
			eib.setMemberLimit(eventList.get(i).getMemberLimit());
			if(eventList.get(i).getOrganizerId().equals(userId)){
				eib.setOrganizerFlg(true);
			} else {
				eib.setOrganizerFlg(false);				
			}
			eibList.add(eib);
		}
		return eibList;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
