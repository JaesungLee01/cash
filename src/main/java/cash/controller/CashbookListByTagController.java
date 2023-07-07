package cash.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.modal.CashbookDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/cashbookListByTag")
public class CashbookListByTagController extends HttpServlet {
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 구현
		HttpSession session = request.getSession();
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
		String memberId = loginMember.getMemberId();
		String word = request.getParameter("word");
		
		//페이징
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		// 페이지당 보여줄 행
		int rowPerPage = 10;
		
		int beginRow = (currentPage - 1) * rowPerPage;
		
		CashbookDao cashbookDao = new CashbookDao();
		
		int totalRow = cashbookDao.cashbookCnt();
		
		int lastPage = totalRow/rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage++;
		}
		
		int pageCount = 10;
		
		int startPage = ((currentPage - 1)/pageCount)*pageCount+1;
		
		int endPage = startPage+9;
		if(endPage > lastPage) {
			endPage = lastPage;
		}
		
		List<Cashbook> list = cashbookDao.selectCashbookListByTag(memberId, word, beginRow, rowPerPage);
				
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("/WEB-INF/view/cashbookListByTag.jsp").forward(request, response);
		
	}

}
