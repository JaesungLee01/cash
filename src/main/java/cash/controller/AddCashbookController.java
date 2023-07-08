package cash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.modal.CashbookDao;
import cash.modal.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Hashtag;
import cash.vo.Member;

@WebServlet("/addCashbook")
public class AddCashbookController extends HttpServlet {
	// 입력폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		// request 매개값
		
		HttpSession session = request.getSession();
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDay = Integer.parseInt(request.getParameter("targetDay"));
		
		String memberId = loginMember.getMemberId();
		String cashbookDate = targetYear + "-" + (targetMonth + 1) + "-" + targetDay;
		
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDay", targetDay);
		request.setAttribute("memberId", memberId);
		request.setAttribute("cashbookDate", cashbookDate);
		
		
		request.getRequestDispatcher("/WEB-INF/view/addCashbook.jsp").forward(request, response);
	}
	// 입력액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		// 유효성검사
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
		
		// return 매개값
		
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDay = Integer.parseInt(request.getParameter("targetDay"));
		int price = Integer.parseInt(request.getParameter("price"));
		String memberId = loginMember.getMemberId();
		String category = request.getParameter("category");
		String memo = request.getParameter("memo");
		String cashbookDate = request.getParameter("cashbookDate");
		System.out.println(targetYear + " <- targetYear");
		System.out.println(targetMonth + " <- targetMonth");
		System.out.println(price + " <- price");
		System.out.println(category + " <- category");
		System.out.println(memo + " <- memo");
		System.out.println(cashbookDate + " <- cashbookDate");
		
		Cashbook cashbook = new Cashbook();
		cashbook.setMemberId(memberId);
		cashbook.setCashbookDate(cashbookDate);
		cashbook.setCategory(category);
		cashbook.setPrice(price);
		cashbook.setMemo(memo);
		
		CashbookDao cashbookDao = new CashbookDao();
		int cashbookNo = cashbookDao.insertCashbook(cashbook);
		if(cashbookNo == 0) {
			System.out.println("입력실패");
			response.sendRedirect(request.getContextPath()+"/addCashbook?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&targetDay=" + targetDay);
			return;
		}
		
		// 입력성공시 -> 해시태그가 있다면 -> 해시태그 추출 -> 해시태급 입력 (반복)
		// 해시태그 추출 알고리즘
		// ##구디 #구디#자바
		String memo2 = memo.replace("#", " #"); // "#구디#아카데미" -> " #구디 #아카데미"
		// 해시태그가 여러개이면 반복해서 입력
		for(String ht : memo2.split(" ")) {
			String ht2 = ht.replace("#", "");
			if(ht2.length() > 0) {
				Hashtag hashtag = new Hashtag();
				hashtag.setCashbookNo(cashbookNo);
				hashtag.setWord(ht2);
				HashtagDao hashtagDao = new HashtagDao();
				hashtagDao.insertHashtag(hashtag);
			}
		}
		// redirect -> cashbookListController
		response.sendRedirect(request.getContextPath()+"/cashbookOne?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&targetDay=" + targetDay);
	}

}
