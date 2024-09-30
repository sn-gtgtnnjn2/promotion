package controller;

import java.io.IOException;
import java.sql.SQLException;
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

import bean.EventInfoBean;
import dao.DaoFactory;
import dao.EntryApprovalDao;
import dao.EventInfoDao;
import dao.ProfileDao;
import dto.EntryApproval;
import dto.Event;
import dto.Profile;

/**
 * Servlet implementation class PortalServlet
 */
@WebServlet("/portal")
public class PortalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		if(Objects.isNull(userId)) {
			// 認証されていない可能性があるので、ログインページに遷移
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
		// プロフィール情報を取得
		ProfileDao pd = DaoFactory.createProfileDao();
		Profile profile = pd.findByUserId(userId);
		System.out.println("imagepath:" + profile.getImagePath());
		
//		// プロフィール画像をエンコード
//		ServletContext ctx = request.getServletContext();
//		String path = ctx.getRealPath(UploadServlet.UPLOAD_DIR);
//		File imgFile = new File(path + "/" +profile.getImagePath());
//		System.out.println("ある？"+imgFile.exists());
//		String strImageData = "";
//		if(imgFile.exists()) {
//			strImageData = Base64ImageEncoder.encodeImage(imgFile.getPath());
//		}
		
		try {
			request.setAttribute("eventInfoList", getEventInfoListForPortal(userId));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		request.setAttribute("strImageData", profile.getBase64Data());
		request.setAttribute("profText", profile.getText());
		request.getRequestDispatcher("/WEB-INF/view/portal.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private List<EventInfoBean> getEventInfoListForPortal(String userId) throws SQLException {
		EntryApprovalDao ead = DaoFactory.createEntryApprovalDao();
		EventInfoDao eid = DaoFactory.createEventInfoDao();
		
		List<EntryApproval> eaList;
			eaList = ead.selectByUserId(userId);
		System.out.println("ead.selectByUserId(userId):" + eaList.size());
		List<Integer> eventIdList = new ArrayList<Integer>();
		
		List<Event> eventList = null;
		// 対象イベントIDリストを生成する。
		if(!Objects.isNull(eaList) && eaList.size() != 0) {			
			for(int i = 0; i < eaList.size(); i ++) {
				System.out.println("event:id" + eaList.get(i).getEventID());
				eventIdList.add(eaList.get(i).getEventID());
			}
		}
		if (!Objects.isNull(eventIdList) && eventIdList.size() != 0) {
			eventList = eid.selectByEventIdsWithPict(eventIdList);
			for (int i = 0; i < eventList.size(); i++) {
				// 参加締め切り前、締め切り後を判定してステータスをセット
				eventList.get(i).setStatus(getEventStatus(eventList.get(i).getRecruitmentStartDate(),
				eventList.get(i).getRecruitmentEndDate()));
			}
		}
		System.out.println("eventList:" + eventList.size());
		
		// イベントリストを作成する
		List<EventInfoBean> eventInfoList = new ArrayList<EventInfoBean>(); 
		for(int i = 0; i < eventList.size(); i ++ ) {
			EventInfoBean eventInfo = new EventInfoBean();
			eventInfo.setEventId(eventList.get(i).getEventId());
			eventInfo.setUserId(eventList.get(i).getUserId());
			eventInfo.setEventTitle(eventList.get(i).getEventTitle());
			eventInfo.setEventDate(eventList.get(i).getEventDate());
			eventInfo.setOrganizerName(eventList.get(i).getOrganizerName());
			eventInfo.setOrganizerId(eventList.get(i).getOrganizerId());
			eventInfo.setScenarioTitle(eventList.get(i).getScenarioTitle());
			eventInfo.setRecruitmentStartDate(eventList.get(i).getRecruitmentStartDate());
			eventInfo.setRecruitmentEndDate(eventList.get(i).getRecruitmentEndDate());
			eventInfo.setMemberLimit(eventList.get(i).getMemberLimit());
			eventInfo.setStatus(getEventStatus(eventList.get(i).getRecruitmentStartDate(), eventList.get(i).getRecruitmentEndDate()));
			eventInfo.setOrganizerImageString(eventList.get(i).getOrganizerImageString());
			eventInfoList.add(eventInfo);
		}
		return eventInfoList;
	}

	private Integer getEventStatus(Date recruitmentStartDate, Date recruitmentEndDate) {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}
}
