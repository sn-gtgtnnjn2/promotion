package controller.event;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import bean.EventAndDetailBean;
import bean.MemberBean;
import controller.NavigationManager;
import dao.DaoFactory;
import dao.EntryApprovalDao;
import dao.EventAndDetailDao;
import dao.FollowsDao;
import dto.EntryApproval;
import dto.EntryApprovalWithPict;
import dto.EventAndDetail;
import dto.Follows;
import util.Constants;
import util.GeneralFormatter;

/**
 * Servlet implementation class Eventdetailervlet
 */
@WebServlet("/event/eventViewOrg")
public class EventDetailOrgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventDetailOrgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		EventAndDetailBean eadbTransition = (EventAndDetailBean) session.getAttribute("eadb");
		String userId = (String) session.getAttribute("userId");
		String strEventId = request.getParameter("eventId");
		String fromStr = request.getParameter("from");
		String backTarget = null;
		if(StringUtils.isEmpty(fromStr)) {
			backTarget = request.getParameter("backTarget");
		} else {
			backTarget = NavigationManager.getServletURL(fromStr);
		}
		String searchQueryAll = request.getQueryString();
		if(StringUtils.isEmpty(searchQueryAll)) {
			searchQueryAll = request.getParameter("searchQuery");
		}
		
		String searchQuery = null;
		if(StringUtils.isNotEmpty(searchQueryAll)) {
			searchQuery = searchQueryAll.replace("eventId=" + strEventId , "");
		}
		System.out.println(searchQuery);
		System.out.println("strEventId->" + strEventId);
		Integer eventId = null;
		
		// 閲覧権限があるか判定
		// @todo
		boolean haveAuth = checkOpenLevel(userId);
		if(!haveAuth) {
			request.getRequestDispatcher(NavigationManager.getServletURL(fromStr)).forward(request, response);
			return;
		}
		
		try {
			eventId = Integer.parseInt(strEventId);
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		EventAndDetailDao eid = DaoFactory.createEventAndDetailDao();
		EntryApprovalDao ead = DaoFactory.createEntryApprovalDao();
		EventAndDetail event = null;
		List<EntryApprovalWithPict> entSignUpList = null;
		List<EntryApprovalWithPict> entApprovedList = null;
		Boolean canSignUp = false;
		Boolean isFollower = false;
		try {
			// イベント情報、詳細情報を取得
			event = eid.findByEventId(eventId);
			// 申請者リストを取得
			entSignUpList = ead.selectByEventIdWithPict(eventId, Constants.EVENT_APPROVAL_SIGNUP);
			// 承認者リストを取得
			entApprovedList = ead.selectByEventIdWithPict(eventId, Constants.EVENT_APPROVAL_AVAILABLE);
			
			// フォロワーかどうかの情報を取得
			FollowsDao flwDao = DaoFactory.createFollowsDao();
			Follows follower = flwDao.findByUserIdAndFlsId(userId, event.getOrganizerName());
			if(!Objects.isNull(follower)) {
				isFollower = true;
			}
			

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		// イベント情報、詳細情報をBeanに格納(イベントとしての参加可能かどうかのステータスが格納されている)
		EventAndDetailBean eadb = null;
		if(!Objects.isNull(eadbTransition)) {
			System.out.println("引き継いだ");
			eadb = eadbTransition;
			System.out.println(eadb.getEventDate());
		} else {
			System.out.println("新規取得");
			eadb = storeEventAndDetailToBean(event, entApprovedList.size());
		}
		
		// 申込者が参加できるかどうかを判定（主催者でない、イベントの閲覧権限があるかどうか)
		List<String> userRejectList = new ArrayList<String>();
		canSignUp = canThisUserSignUp(userId, event.getOrganizerId(), event.getOpenLevel(), entSignUpList, isFollower, userRejectList);
			eadb.setUserRejectList(userRejectList);
			eadb.setIsAvailableUser(canSignUp);
			
		// 主催者かどうか判定
		boolean oFlg = false;
		if(event.getOrganizerId().equals(userId)) {
			oFlg = true;
		} else {
			oFlg = false;
		}

		// 参加者一覧情報をBeanに追加
		List<MemberBean> signUpMemberPictList = storeMemberInfo(entSignUpList);
		List<MemberBean> approveMemberPictList = storeMemberInfo(entApprovedList);
		request.setAttribute("canSignUp", canSignUp);
		request.setAttribute("backTarget", backTarget);
		request.setAttribute("eadb", eadb);
		request.setAttribute("signUpMemberPictList", signUpMemberPictList);
		request.setAttribute("approveMemberPictList", approveMemberPictList);
		request.setAttribute("screenId", NavigationManager.SCREEN_EVENT_DETAIL_VIEW);
		request.setAttribute("eventId", eventId);
		request.setAttribute("organizerFlg", oFlg);
		
		// 検索画面のクエリパラメータをセット
		request.setAttribute("searchQuery", searchQuery);
		request.setAttribute("backTarget", backTarget);
		
		// デバッグ
		System.out.println(getClass().toString() + ":eadb.status->" + eadb.getStatus());
		System.out.println(getClass().toString() + ":eadb.isAvailableUser->" + eadb.getIsAvailableUser());
		
		request.getRequestDispatcher("/WEB-INF/view/event/event_detail_org.jsp").forward(request, response);
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
		//HttpSession session = request.getSession();
		//String userId = (String) session.getAttribute("userId");
		
        // 承認済みユーザーのデータを取得
        String[] approvedUsers = request.getParameterValues("approvedUsers[]");
        // 申請中ユーザーのデータを取得
        String[] pendingUsers = request.getParameterValues("pendingUsers[]");
        // 却下されたユーザーのデータを取得
        String[] rejectedUsers = request.getParameterValues("rejectedUsers[]");
        // イベントIdを取得
        String strEventId = request.getParameter("eventId");
        String searchQuery = request.getParameter("searchQuery");
        String backTarget = request.getParameter("backTarget");
        		
        Integer eventId;
		try {
			eventId = Integer.parseInt(strEventId);
		}catch(NumberFormatException e) {
			e.printStackTrace();
			doGet(request, response);
			return;
		}
		
		// イベントに紐づく参加者を全部取得
		// 申請者リストを取得
		EntryApprovalDao ead = DaoFactory.createEntryApprovalDao();
		//EventAndDetail event = null;
		List<EntryApproval> memberList = null;
		memberList = ead.selectByEventId(eventId);
		
		// ユーザー名をキーにハッシュマップに入れる
		HashMap<String, EntryApproval> memberHashMap = storeListToHashMap(memberList);
		
		// リクエストで送られてきた情報と照合し、異なった場合は更新対象Listに格納する
		List<EntryApproval> approveTargetList = null;
		List<EntryApproval> pendingTargetList = null;
		List<EntryApproval> rejectTargetList = null;
		if ((!Objects.isNull(approvedUsers)) && approvedUsers.length > 0) {
			approveTargetList = checkUpdateTarget(memberHashMap, approvedUsers, Constants.EVENT_APPROVAL_AVAILABLE);
		}
		if ((!Objects.isNull(pendingUsers)) && pendingUsers.length > 0) {
			pendingTargetList = checkUpdateTarget(memberHashMap, pendingUsers, Constants.EVENT_APPROVAL_SIGNUP);
		}
		if ((!Objects.isNull(rejectedUsers)) && rejectedUsers.length > 0) {
			rejectTargetList = checkUpdateTarget(memberHashMap, rejectedUsers, Constants.EVENT_APPROVAL_REJECT);
		}
		
		// 更新操作
		//EntryApprovalDao ead = DaoFactory.createEntryApprovalDao();
		
		try {
			if ((!Objects.isNull(approveTargetList)) && approveTargetList.size() > 0) {
				ead.updateApproveStatus(approveTargetList, Constants.EVENT_APPROVAL_AVAILABLE);
			}
			if ((!Objects.isNull(pendingTargetList)) && pendingTargetList.size() > 0) {
				ead.updateApproveStatus(pendingTargetList, Constants.EVENT_APPROVAL_SIGNUP);
			}
			if ((!Objects.isNull(rejectTargetList)) && rejectTargetList.size() > 0) {
				ead.updateApproveStatus(rejectTargetList, Constants.EVENT_APPROVAL_REJECT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		request.setAttribute("eventId", eventId);
		request.setAttribute("backTarget", backTarget);
		request.setAttribute("searchQuery", searchQuery);
		
		doGet(request, response);
		// 再度詳細画面へ
		//request.getRequestDispatcher("/event/eventViewOrg?" + searchQuery).forward(request, response);
		
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
