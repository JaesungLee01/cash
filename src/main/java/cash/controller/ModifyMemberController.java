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

@WebServlet("/modifyMember")
public class ModifyMemberController extends HttpServlet {

    // 회원 정보 수정 폼 보여주기
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 현재 로그인한 회원 정보 가져오기
    	HttpSession session = request.getSession();
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");
        String memberId = loginMember.getMemberId();
        if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
        
        MemberDao memberDao = new MemberDao();

        // 회원 정보 조회
        Member member = memberDao.selectMemberById(loginMember);

        // 회원 정보를 폼에 전달
        request.setAttribute("member", member);
        request.getRequestDispatcher("/WEB-INF/view/modifyMember.jsp").forward(request, response);
    }

    // 회원 정보 수정 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 현재 로그인한 회원 정보 가져오기
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");
        String memberId = loginMember.getMemberId();

        // 수정할 회원 정보 파라미터에서 가져오기
        String memberPw = request.getParameter("memberPw");
        String newPw = request.getParameter("newPw");
        String newCkPw = request.getParameter("newCkPw");

        MemberDao memberDao = new MemberDao();

        // 새로운 비밀번호와 확인 비밀번호 일치 여부 확인
        if (!newPw.equals(newCkPw)) {
            // 비밀번호 불일치 시
        	System.out.println("비밀번호가 서로 다릅니다");
        	response.sendRedirect(request.getContextPath()+"/memberOne");
            return;
        }

        // 회원 비밀번호 업데이트
        int row = memberDao.updateMember(memberId, newPw);

        if (row == 1) {
            // 비밀번호 변경 성공 시
        	System.out.println("비밀번호 변경 성공");
        	response.sendRedirect(request.getContextPath()+"/memberOne");
        } else {
            // 비밀번호 변경 실패 시
        	System.out.println("비밀번호 변경 실패");
        	response.sendRedirect(request.getContextPath()+"/memberOne");
        }
    }
}