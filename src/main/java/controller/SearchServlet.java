package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.EventInfoBean;
import dao.DaoFactory;
import dao.EventInfoDao;
import dto.Event;
import dto.EventAndDetail;

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
		EventInfoDao eid = DaoFactory.createEventInfoDao();
		String whereStr = "WHERE event.recruitment_start_date <= now()"
				+ " AND event.recruitment_end_date > now()"
				+ " AND event.cancel_flg <> 1"
				+ " AND event.delete_flg <> 1";
		System.out.println(whereStr);
		List<Event> eventList = eid.selectAvailableEventsWithPict(100, whereStr);
		List<EventInfoBean> eventInfoList = storeEventToBean(eventList);
		System.out.println(eventInfoList.size());
		
		request.setAttribute("eventInfoList", eventInfoList);
		request.getRequestDispatcher("/WEB-INF/view/search.jsp").forward(request, response);
	}

	private List<EventInfoBean> storeEventToBean(List<Event> eventList) {
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
