package controller.event;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EventAndDetailBean;
import bean.MemberBean;
import dao.DaoFactory;
import dao.EventAndDetailDao;
import dto.EntryApproval;
import dto.EntryApprovalWithPict;
import dto.EventAndDetail;
import util.DateValidator;
import util.GeneralFormatter;
import util.StringValidator;

/**
 * Servlet implementation class Eventdetailervlet
 */
@WebServlet("/event/editEventConfirm")
public class EditEventConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEventConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		EventAndDetailBean beforeEadb = (EventAndDetailBean) session.getAttribute("eadb"); 
		EventAndDetailBean newEadb = storeEventAndDetailBeanFromRequest(request, beforeEadb);
		
        // ページ遷移管理情報を取得
        String strEventId = request.getParameter("eventId");
        String searchQuery = request.getParameter("searchQuery");
        String backTarget = request.getParameter("backTarget");
		
		request.setAttribute("eadb", newEadb);
		session.setAttribute("eadb", newEadb);
		System.out.println("eadb:" + beforeEadb.getEventId());
		System.out.println("eadb:" + newEadb.getEventId());
		request.getRequestDispatcher("/WEB-INF/view/event/event_detail_org_confirm.jsp").forward(request, response);
	}

	private EventAndDetailBean storeEventAndDetailBeanFromRequest(HttpServletRequest request, EventAndDetailBean beforeEadb) {
		EventAndDetailBean eadb = new EventAndDetailBean();
		
		// リクエストから必要なパラメータを取得
		String strEventId = request.getParameter("eventId");
		String eventTitle = request.getParameter("eventTitle");
		String strEventDate = request.getParameter("eventDate");
		String strEventTime = request.getParameter("eventTime");
		String strRecruitmentStartDate = request.getParameter("recruitmentStartDate");
		String strRecruitmentStartTime = request.getParameter("recruitmentStartTime");
		String strRecruitmentEndDate = request.getParameter("recruitmentEndDate");
		String strRecruitmentEndTime = request.getParameter("recruitmentEndTime");
		String scenarioTitle = request.getParameter("scenarioTitle");
		String strStatus = request.getParameter("eventStatus");
		String detail = request.getParameter("details");
		System.out.println(getClass().getName());
		System.out.println(strEventDate);
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
		Integer eventId = null;
		Integer memberLimit = null;
		Integer openLevel = null;
		Integer status = null;
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
		
		try {
			eventId = Integer.parseInt(strEventId);
		} catch (NumberFormatException e) {
			errorList.add(String.format("イベントIDの取得に失敗しました。"));
			e.printStackTrace();
		}
		
		try {
			status = Integer.parseInt(strStatus);
		} catch (NumberFormatException e) {
			errorList.add(String.format("ステータスの値が不正です"));
			e.printStackTrace();
		}
		
			eadb.setEventId(eventId);
System.out.println("newEventDate" + eventDate);
			eadb.setEventDate(eventDate);
			eadb.setEventTitle(eventTitle);
			eadb.setOrganizerName(beforeEadb.getOrganizerName());
			eadb.setScenarioTitle(scenarioTitle);
			eadb.setDetail(detail);
			eadb.setMemberLimit(memberLimit);
			eadb.setopenLevel(openLevel);
			eadb.setRecruitmentStartDate(recruitmentStartDate);
			eadb.setRecruitmentEndDate(recruitmentEndDate);

			// イベントが募集状況かどうかを判定（人数制限以内、募集期間内）
			eadb.setStatus(beforeEadb.getStatus());

		return eadb;
	}

	private Boolean canThisUserSignUp(String userId, String organizerId, Integer openLevel,
			List<EntryApprovalWithPict> entAppList, Boolean isFollower, List<String> userRejectList) {
		// ユーザーが主催者でないことを確認
		if(userId.equals(organizerId)) {
			userRejectList.add("主催者は参加表明できません");
			System.out.println("ユーザーが主催者です");
			return false;
		}
		
		// 公開レベルが全体以外のとき、ユーザーに閲覧権限があるかを確認
		if(openLevel != 0) {
			if(!isFollower) {
				userRejectList.add("フォロワー向けイベントには、フォロー外のユーザーは参加できません");
				System.out.println("フォロワーじゃない");
				return false;
			}
		}
		
		// 既に申請していないことを確認
		for(int i = 0; i < entAppList.size(); i++) {
			if(entAppList.get(i).getSignUpUserId().equals(userId)) {
				userRejectList.add("既に申請しています");
				System.out.println("既に申請済み");
				return false;
			}
		}
		return true;
	}

	private boolean checkOpenLevel(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	private List<MemberBean> storeMemberInfo(List<EntryApprovalWithPict> entAppList) {
		// TODO 自動生成されたメソッド・スタブ
		List<MemberBean> memberList = new ArrayList<MemberBean>();
		for(int i = 0 ; i < entAppList.size(); i ++) {
			MemberBean member = new MemberBean();
			member.setUserId(entAppList.get(i).getSignUpUserId());
			member.setUserName(entAppList.get(i).getUserName());
			member.setSignUpDateStr(GeneralFormatter.toUsualString(entAppList.get(i).getUpdateDatetime()));
			member.setImageString(entAppList.get(i).getBase64ImgStr());
			memberList.add(member);
		}
		return memberList;
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
			System.out.println("イベント情報の更新成功");
		} catch (SQLException e) {
			System.out.println("イベント情報の更新失敗");
			e.printStackTrace();
		}
		
		// セッション削除
		session.removeAttribute("eadb");
		System.out.println("セッション削除");
System.out.println("update終わり");		
		
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

	private List<EntryApproval> checkUpdateTarget(HashMap<String, EntryApproval> memberHashMap,
			String[] userIds, Integer checkStatus) {
		 List<EntryApproval> updateTargetList = new ArrayList<EntryApproval>();
		 for(int i = 0; i < userIds.length; i ++) {
			 if(memberHashMap.get(userIds[i]).getApprovalStatus() != checkStatus) {
				 updateTargetList.add(memberHashMap.get(userIds[i]));
			 } else {
				 // DB情報と変わらないため、処理対象としない
			 }
		 }
		return updateTargetList;
	}

	private HashMap<String, EntryApproval> storeListToHashMap(List<EntryApproval> memberList) {
		HashMap<String, EntryApproval> memberHashMap = new HashMap<String, EntryApproval>();
		for(int i = 0; i < memberList.size(); i ++) {
			memberHashMap.put(memberList.get(i).getSignUpUserId(), memberList.get(i));
		}
		return memberHashMap;
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
