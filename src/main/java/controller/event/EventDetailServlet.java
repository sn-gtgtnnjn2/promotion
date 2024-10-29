package controller.event;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CharaInfoForEventDetailBean;
import bean.EventAndDetailBean;
import bean.MemberBean;
import controller.NavigationManager;
import dao.DaoFactory;
import dao.EntryApprovalDao;
import dao.EventAndDetailDao;
import dao.FollowsDao;
import dao.ScenarioEntriedCharaDao;
import dto.CharasForEventDetailDto;
import dto.EntryApprovalWithPict;
import dto.EventAndDetail;
import dto.Follows;
import util.Constants;
import util.GeneralFormatter;

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
		String fromStr = request.getParameter("from");
		String searchQuery = request.getQueryString().replace("eventId=" + strEventId , "");
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
		ScenarioEntriedCharaDao secd = DaoFactory.createScenarioEntriedCharaDao();
		
		EventAndDetail event = null;
		List<EntryApprovalWithPict> entAppList = null;
		List<EntryApprovalWithPict> entSignUpList = null;
		List<CharasForEventDetailDto> charaList = null;
		Boolean canSignUp = false;
		Boolean isFollower = false;
		Integer loginUserAppStatus = null;
		
		try {
			// イベント情報、詳細情報を取得
			event = eid.findByEventId(eventId);
			
			// 参加承認情報を取得
			entAppList = ead.selectByEventIdWithPict(eventId, event.getOrganizerId(), Constants.EVENT_APPROVAL_AVAILABLE);
			entSignUpList = ead.selectByEventIdWithPict(eventId, event.getOrganizerId(), Constants.EVENT_APPROVAL_SIGNUP);
			
			// ログインユーザーの参加承認ステータスを取得
			loginUserAppStatus = ead.getApprovalStatus(eventId, userId);
			
			// 参加予定キャラクターを取得
			charaList = secd.getEventEntryCharas(eventId);
			
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
		EventAndDetailBean eadb = storeEventAndDetailToBean(event, entAppList.size());
		
		// キャラクター情報をbeanに格納
		List<CharaInfoForEventDetailBean> charaListForScreen = storeCharaListToBean(charaList, userId);
		
		// 申込者が参加できるかどうかを判定（主催者でない、イベントの閲覧権限があるかどうか)
		List<String> userRejectReasonList = new ArrayList<String>();
		canSignUp = canThisUserSignUp(userId, event.getOrganizerId(), event.getOpenLevel(), entAppList, isFollower, userRejectReasonList);
			eadb.setUserRejectList(userRejectReasonList);
			eadb.setIsAvailableUser(canSignUp);

			// ログインユーザーの参加承認状況を取得
			String userApprovalStatusName = null;
			if(!Objects.isNull(loginUserAppStatus)) {
				userApprovalStatusName = Constants.getApprovalStatusName(loginUserAppStatus);
			} else {
				userApprovalStatusName = "未申請";				
			}
			
		System.out.println("charaForScreenSize" + charaListForScreen.size());
		// 参加者一覧情報をBeanに追加
		List<MemberBean> memberPictList = storeMemberInfo(entAppList);
		List<MemberBean> signUpMemberPictList = storeMemberInfo(entSignUpList);
		request.setAttribute("canSignUp", canSignUp);
		request.setAttribute("backTarget", NavigationManager.getServletURL(fromStr));
		request.setAttribute("eadb", eadb);
		request.setAttribute("charaListForScreen", charaListForScreen);
		request.setAttribute("memberPictList", memberPictList);
		request.setAttribute("signUpMemberPictList", signUpMemberPictList);
		request.setAttribute("screenId", NavigationManager.SCREEN_EVENT_DETAIL_VIEW);
		request.setAttribute("userApprovalStatusName", userApprovalStatusName);
		request.setAttribute("approveStatus", loginUserAppStatus);
		System.out.println("approveStatus:" + loginUserAppStatus);
		
		// 検索画面のクエリパラメータをセット
		request.setAttribute("searchQuery", searchQuery);
		
		// デバッグ
		System.out.println(getClass().toString() + ":eadb.status->" + eadb.getStatus());
		System.out.println(getClass().toString() + ":eadb.isAvailableUser->" + eadb.getIsAvailableUser());
		
		request.getRequestDispatcher("/WEB-INF/view/event/event_detail.jsp").forward(request, response);
	}

	private List<CharaInfoForEventDetailBean> storeCharaListToBean(List<CharasForEventDetailDto> charaList, String loginUser) {
		List<CharaInfoForEventDetailBean> newCharaList = new ArrayList<CharaInfoForEventDetailBean>();
		for(int i = 0; i < charaList.size(); i ++) {
			CharaInfoForEventDetailBean bean = new CharaInfoForEventDetailBean();
			bean.setCharacterId(charaList.get(i).getCharacterId());
			bean.setEventId(charaList.get(i).getEventId());
			bean.setPlayerId(charaList.get(i).getPlayerId());
			bean.setPlayerName(charaList.get(i).getPlayerName());
			bean.setName(charaList.get(i).getName());
			bean.setNameKana(charaList.get(i).getNameKana());
			bean.setMemo(charaList.get(i).getMemo());
			bean.setExternalLink(charaList.get(i).getExternalLink());
			bean.setImageFileName(charaList.get(i).getImageFilename());
			bean.setImageFilePath(charaList.get(i).getImagePath());
			if(charaList.get(i).getPlayerId().equals(loginUser)) {
				bean.setIsLoginUserOwner(true);
			} else {
				bean.setIsLoginUserOwner(false);
			}
			newCharaList.add(bean);
		}
		return newCharaList;
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
				//if(entAppList.get(i).getSignUpUserId().equals(userId)) {
				if(entAppList.get(i).getApprovalStatus().equals(Constants.EVENT_APPROVAL_SIGNUP)) {
					userRejectList.add("既に申請しています");
					System.out.println("既に申請済み");
					return false;
				}
				if(entAppList.get(i).getApprovalStatus().equals(Constants.EVENT_APPROVAL_AVAILABLE)) {
					userRejectList.add("既に承認しています");
					System.out.println("既に承認済み");
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkOpenLevel(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

//	private LinkedHashMap<String, String> storeMemberInfo(List<EntryApprovalWithPict> entAppList) {
//		// TODO 自動生成されたメソッド・スタブ
//		LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<String,String>();
//		for(int i = 0 ; i < entAppList.size(); i ++) {
//			linkedHashMap.put(entAppList.get(i).getUserName(),entAppList.get(i).getBase64ImgStr());
//		}
//		return linkedHashMap;
//	}

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
}
