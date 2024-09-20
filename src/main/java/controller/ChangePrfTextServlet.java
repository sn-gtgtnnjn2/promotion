package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.StringValidator;

/**
 * Servlet implementation class ChangePrfTextServlet
 */
@WebServlet("/changePrfText")
public class ChangePrfTextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePrfTextServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		String text = (String) request.getParameter("profText");
		System.out.println("そうしんされた"+text);
		
		// エラー
		List<String> errorList = new ArrayList<String>();
		StringValidator sv = new StringValidator();
		
		// バリデーション
		// 必須チェック 文字種チェック　長さチェック
		if(!sv.validate(text, 0, 200, StringValidator.NO_CHECK, "プロフィール本文")) {
			errorList.addAll(sv.getErrorList());
		}
		sv.clearErrorMessage();
		
		// 一個以上エラーがあれば、内容とエラーメッセージを登録
		if(errorList.size() > 0) {
			request.setAttribute("profText", text);
			request.setAttribute("errorListChild", errorList);
		}
		
		
		request.getRequestDispatcher("/portal").forward(request, response);
	}
	

}
