package controller.event;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.EventAndDetailBean;
import dao.DaoFactory;
import dao.EventAndDetailDao;
import dto.EventAndDetail;

/**
 * Servlet implementation class EventViewServlet
 */
@WebServlet("/sseventView")
public class EventViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// エラーリスト
		List<String> errorList = new ArrayList<String>();
		
		String eventId = (String) request.getParameter("eventId");
		EventAndDetailDao eadd = DaoFactory.createEventAndDetailDao();
		EventAndDetail ead = null;
		try {
			ead = eadd.findByEventId(Integer.parseInt(eventId));			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			errorList.add(String.format("イベント情報の取得に失敗しました"));
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/portal").forward(request, response);
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			errorList.add(String.format("イベント情報の取得に失敗しました"));
			request.setAttribute("errorList", errorList);
			request.getRequestDispatcher("/portal").forward(request, response);
		}
		
		EventAndDetailBean eadb = new EventAndDetailBean();
		eadb.setEventId(ead.getEventId());
		eadb.setEventTitle(ead.getEventTitle());
		eadb.setEventDate(ead.getEventDatetime());
		eadb.setUserId(ead.getUserId());
		eadb.setOrganizerId(ead.getOrganizerId());
		eadb.setOrganizerName(ead.getOrganizerName());
		eadb.setScenarioTitle(ead.getScenarioTitle());
		eadb.setDetail(ead.getDetail());
		eadb.setRecruitmentStartDate(ead.getRecruitmentStartDate());
		eadb.setRecruitmentEndDate(ead.getRecruitmentEndDate());
		eadb.setMemberLimit(ead.getMemberLimit());
		eadb.setopenLevel(ead.getOpenLevel());
		eadb.setStatus(ead.getStatus());
		
		request.setAttribute("eventAndDetailList", eadb);
		request.getRequestDispatcher("/WEB-INF/view/event/view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
