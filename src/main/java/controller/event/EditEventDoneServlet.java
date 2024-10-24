package controller.event;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EventAndDetailBean;
import dao.DaoFactory;
import dao.EventAndDetailDao;
import dto.EventAndDetail;

/**
 * Servlet implementation class Eventdetailervlet
 */
@WebServlet("/event/editEventDone")
public class EditEventDoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEventDoneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	doPost(request, response);
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		EventAndDetailBean eadb = (EventAndDetailBean) session.getAttribute("eadb");

        // イベントIdを取得
        String strEventId = request.getParameter("eventId");
        String searchQuery = request.getParameter("searchQuery");
        String backTarget = request.getParameter("backTarget");
		
		// 更新操作
		//EntryApprovalDao ead = DaoFactory.createEntryApprovalDao();
		
        Integer eventId = null;
		try {
				eventId = Integer.parseInt(strEventId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("eventId", eventId);
		request.setAttribute("backTarget", backTarget);
		request.setAttribute("searchQuery", searchQuery);
		
		EventAndDetail ead = storeBeanToDto(eadb, eventId);
		
		try {
			EventAndDetailDao eadd = DaoFactory.createEventAndDetailDao();
			eadd.updateRow(ead);
			System.out.println("イベント更新処理完了");
		} catch (SQLException e) {
			System.out.println("イベント情報の更新失敗");
			e.printStackTrace();
		}
		
		// セッション削除
		session.removeAttribute("eadb");
		System.out.println("セッション削除");
		
		// 再度詳細画面へ
		request.getRequestDispatcher("/event/eventViewOrg?" + searchQuery).forward(request, response);
		
	}
	
	private EventAndDetail storeBeanToDto(EventAndDetailBean eadb, Integer eventId) {
		EventAndDetail ead = new EventAndDetail();
		ead.setEventId(eventId);
		ead.setDetail(eadb.getDetail());
		ead.setEventDatetime(eadb.getEventDate());
		ead.setEventId(eadb.getEventId());
		ead.setEventTitle(eadb.getEventTitle());
		ead.setMemberLimit(eadb.getMemberLimit());
		ead.setOpenLevel(eadb.getopenLevel());
		ead.setOrganizerId(eadb.getOrganizerId());
		ead.setOrganizerName(eadb.getOrganizerName());
		ead.setRecruitmentEndDate(eadb.getRecruitmentEndDate());
		ead.setRecruitmentStartDate(eadb.getRecruitmentStartDate());
		ead.setScenarioTitle(eadb.getScenarioTitle());
		ead.setStatus(eadb.getStatus());
		ead.setCancelFlg(eadb.getCancelFlg());
		return ead;
	}

	EventAndDetailBean storeEventAndDetailToBean(EventAndDetail ead, int signUpUserList){
		EventAndDetailBean eadb = new EventAndDetailBean();
		if (!Objects.isNull(ead)) {
			eadb.setEventId(ead.getEventId());
			eadb.setEventDate(ead.getEventDatetime());
			eadb.setEventTitle(ead.getEventTitle());
			eadb.setOrganizerName(ead.getOrganizerName());
			eadb.setScenarioTitle(ead.getScenarioTitle());
			eadb.setDetail(ead.getDetail());
			eadb.setMemberLimit(ead.getMemberLimit());
			eadb.setopenLevel(ead.getOpenLevel());
			eadb.setRecruitmentStartDate(ead.getRecruitmentStartDate());
			eadb.setRecruitmentEndDate(ead.getRecruitmentEndDate());

			// イベントが募集状況かどうかを判定（人数制限以内、募集期間内）
			eadb.setStatus(EventAndDetail.getIsAvailable(ead, signUpUserList));
		}
		return eadb;
	}
}
