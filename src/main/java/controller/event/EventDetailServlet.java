package controller.event;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EventAndDetailBean;
import dao.DaoFactory;
import dao.EntryApprovalDao;
import dao.EventAndDetailDao;
import dto.EntryApprovalWithPict;
import dto.EventAndDetail;

/**
 * Servlet implementation class Eventdetailervlet
 */
@WebServlet("/event/eventView")
public class EventDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String strEventId = request.getParameter("eventId");
		System.out.println("strEventId->" + strEventId);
		Integer eventId = null;
		
		// 閲覧権限があるか判定
		// @todo
		
		try {
			eventId = Integer.parseInt(strEventId);
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		EventAndDetailDao eid = DaoFactory.createEventAndDetailDao();
		EntryApprovalDao ead = DaoFactory.createEntryApprovalDao();
		EventAndDetail event = null;
		List<EntryApprovalWithPict> entAppList = null;
		try {
			// イベント情報、詳細情報を取得
			event = eid.findByEventId(eventId);
			// 参加承認情報を取得
			entAppList = ead.selectByEventIdWithPict(eventId);
			
			// 申込者が参加できるかどうかを判定（主催者でない、イベントの閲覧権限があるかどうか）

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		// イベント情報、詳細情報をBeanに格納
		EventAndDetailBean eadb = storeEventAndDetailToBean(event, entAppList.size());
		
		// 参加者一覧情報をBeanに追加
		LinkedHashMap<String,String> memberPictList = storeMemberInfo(entAppList);
		
		request.setAttribute("eadb", eadb);
		request.setAttribute("memberPictList", memberPictList);
		request.getRequestDispatcher("/WEB-INF/view/event/event_detail.jsp").forward(request, response);
	}

	private LinkedHashMap<String, String> storeMemberInfo(List<EntryApprovalWithPict> entAppList) {
		// TODO 自動生成されたメソッド・スタブ
		LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<String,String>();
		for(int i = 0 ; i < entAppList.size(); i ++) {
			linkedHashMap.put(entAppList.get(i).getUserName(),entAppList.get(i).getBase64ImgStr());
		}
		return linkedHashMap;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	EventAndDetailBean storeEventAndDetailToBean(EventAndDetail ead, int signUpUserList){
		EventAndDetailBean eadb = new EventAndDetailBean();
		if (!Objects.isNull(ead)) {
			eadb.setEventDate(ead.getEventDatetime());
			eadb.setEventTitle(ead.getEventTitle());
			eadb.setOrganizerName(ead.getOrganizerName());
			eadb.setScenarioTitle(ead.getScenarioTitle());
			eadb.setDetail(ead.getDetail());
			eadb.setMemberLimit(ead.getMemberLimit());
			// イベントが募集状況かどうかを判定（人数制限以内、募集期間内）
			eadb.setStatus(EventAndDetail.getIsAvailable(ead, signUpUserList));
		}
		return eadb;
	}


}
