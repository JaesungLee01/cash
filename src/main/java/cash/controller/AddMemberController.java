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

@WebServlet("/addMember")
public class AddMemberController extends HttpServlet {
   
//   addMember.jsp 회원가입 폼
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      session 유효성 검사(null)
      HttpSession session = request.getSession();
      if(session.getAttribute("loginMember") != null) {
         response.sendRedirect(request.getContextPath() + "/login");
         return;
      }
//      jsp페이지로 포워드(디스패치)
      request.getRequestDispatcher("/WEB-INF/view/addMember.jsp").forward(request, response);
   }
   
//   회원가입 액션
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      session 유효성 검사(null)
      HttpSession session = request.getSession();
      if(session.getAttribute("loginMember") != null) {
         response.sendRedirect(request.getContextPath() + "/login");
         return;
      }
      
//      request.getParameter()
      String memberId = request.getParameter("memberId");
//      System.out.println(memberId + " <- memberId");
      String memberPw = request.getParameter("memberPw");
//      System.out.println(memberPw + " <- memberPw");
      Member member = new Member(memberId, memberPw, null, null);
      
//      회원가입 DAO호출
      MemberDao memberDao = new MemberDao();
      int row = memberDao.insertMember(member);
      if (row == 0) { // 회원가입 실패시
//         addMember.jsp view를 이동하는 controller를 리다이렉트
//         기존에 사용했던 데이터 남겨서 보내기 위함
         request.getRequestDispatcher("/WEB-INF/view/addMember.jsp").forward(request, response);
         return;
      } else if (row == 1) {
//         login.jsp view를 이동하는 controller를 리다이렉트
         response.sendRedirect(request.getContextPath() + "/login");
         return;
      } else {
         System.out.println("add member error");
      }
   }
}