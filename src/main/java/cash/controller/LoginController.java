package cash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.modal.MemberDao;
import cash.vo.Member;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	@Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath()+"/cashbook");
		}
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
   }

	@Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		Member member = new Member(memberId, memberPw, null, null);
		
		MemberDao memberDao = new MemberDao();
		Member loginMember = memberDao.selectMemberById(member);
		// null일떄
		
		if(loginMember == null) { // 로그인실패
			System.out.println("로그인실패");
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		} 
		// 로그인 성공시 : session 사용
		HttpSession session = request.getSession();
		System.out.println("로그인성공");
		session.setAttribute("loginMember", loginMember);
		response.sendRedirect(request.getContextPath()+"/cashbook");
		
   }

}