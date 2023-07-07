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

@WebServlet("/removeMember")
public class RemoveMemberController extends HttpServlet {

   // 비밀번호 입력 폼
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession();
      
      Member loginMember = (Member) session.getAttribute("loginMember");
      if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
      
      MemberDao memberDao = new MemberDao();
      Member member = memberDao.selectMemberOne(loginMember.getMemberId());
      request.setAttribute("member", member);
      
      if(session.getAttribute("loginMember") == null) {
         response.sendRedirect(request.getContextPath()+"/login");
         return;
      }
      request.getRequestDispatcher("/WEB-INF/view/removeMember.jsp").forward(request, response);
   }

   // 탈퇴
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession();
      Member loginMember = (Member)session.getAttribute("loginMember");
      
      MemberDao memberDao = new MemberDao();
      Member member = memberDao.selectMemberOne(loginMember.getMemberId());
      
      String memberPw = request.getParameter("memberPw");
      MemberDao memberdao = new MemberDao();
      Member memberId = memberdao.selectMemberOne(loginMember.getMemberId());
      request.setAttribute("member", member);
      
      int row = memberdao.removeMember(memberId,memberPw);
      
      if(row==0) { // 회원탈퇴 실패시
         System.out.println("회원탈퇴 실패");
         response.sendRedirect(request.getContextPath()+"/removeMember");
      } else if(row==1) { // 회원탈퇴 성공시
         // login.jsp 뷰를 이동하는 controller를 리다이렉트
         System.out.println("회원탈퇴 성공");
         session.invalidate();
         response.sendRedirect(request.getContextPath()+"/login");
      } else {
         System.out.println("remove member error");
      }
   }
}
