package controller.event;

import java.io.IOException;

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
 * Servlet implementation class RegistEventServlet
 */
@WebServlet("/event/registEvent")
public class RegistEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistEventServlet() {
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
		HttpSession session = request.getSession();
		EventAndDetailBean eadb = (EventAndDetailBean) session.getAttribute("eadb");
		EventAndDetailDao eadd = DaoFactory.createEventAndDetailDao();
		
		// BeanからDtoを作成
		EventAndDetail ead = new EventAndDetail();
		ead.setEventTitle(eadb.getEventTitle());
		ead.setEventDatetime(eadb.getEventDate());
		ead.setUserId(eadb.getUserId());
		ead.setOrganizerName(eadb.getOrganizerName());
		ead.setOrganizerId(eadb.getUserId());
		ead.setScenarioTitle(eadb.getScenarioTitle());
		ead.setDetail(eadb.getDetail());
		ead.setRecruitmentStartDate(eadb.getRecruitmentStartDate());
		ead.setRecruitmentEndDate(eadb.getRecruitmentEndDate());
		ead.setMemberLimit(eadb.getMemberLimit());
		ead.setopenLevel(eadb.getopenLevel());
		ead.setStatus(eadb.getStatus());
		eadd.insert(ead);
		session.removeAttribute("eadb");
		request.setAttribute("info", "イベントが追加されました");
		request.getRequestDispatcher("/portal").forward(request, response);
	}

}
