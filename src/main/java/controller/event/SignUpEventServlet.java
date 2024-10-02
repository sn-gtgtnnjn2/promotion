package controller.event;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import controller.NavigationManager;
import dao.DaoFactory;
import dao.EntryApprovalDao;
import dto.EntryApproval;

/**
 * Servlet implementation class SignUpEventServlet
 */
@WebServlet("/event/signUpEvent")
public class SignUpEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("aaaaaaaa"+getClass().toString());
		HttpSession session = request.getSession();
		String strEventId = (String) request.getParameter("eventId");
		String userId = (String) session.getAttribute("userId");
		System.out.println(strEventId);
		// エラーリスト
		
		// バリデーション
		if(StringUtils.isEmpty(strEventId)) {
			//request.setAttribute("eventId", eventId)
			request.getRequestDispatcher("/search").forward(request, response);
			return;
		}
		
		Integer eventId = null;
		try {
			eventId = Integer.parseInt(strEventId);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		// 参加状況テーブルにレコードを追加
		EntryApprovalDao ead = DaoFactory.createEntryApprovalDao();
		EntryApproval ea = new EntryApproval();
		ea.setEventID(eventId);
		ea.setSignUpUserId(userId);
		try {
			ead.insert(ea);
		} catch (SQLException e) {
			System.out.println("参加承認テーブルへのインサートに失敗しました。");
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		request.setAttribute("eventId", eventId);
		request.getRequestDispatcher(NavigationManager.getServletURL(NavigationManager.SCREEN_EVENT_DETAIL_VIEW)).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
