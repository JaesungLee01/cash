package cash.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthConstants;

import cash.modal.CashbookDao;
import cash.modal.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/calendar")
public class CalendarController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사
		HttpSession session = request.getSession();
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
		String memberId = loginMember.getMemberId();
		
		// view에 넘겨줄 달력정보(모델값)
		Calendar firstDay = Calendar.getInstance(); // 오늘날짜
		
		
		int targetYear = firstDay.get(Calendar.YEAR);
		int targetMonth = firstDay.get(Calendar.MONTH);
		firstDay.set(Calendar.DATE, 1);
		
		// 출력하고자하는 년도와 월이 매개값으로 넘어왔다면
		if(request.getParameter("targetYear") != null && request.getParameter("targetMonth") !=null ) {
			
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
			
			firstDay.set(Calendar.YEAR, targetYear);
			// API에서 자동으로 Calendar.MONTH값으로 12가 입력되면 월은 1, 년 +1
			// API에서 자동으로 Calendar.MONTH값으로 -1이 입력되면 월은 12, 년 -1
			firstDay.set(Calendar.MONTH, targetMonth);
			
			targetYear = firstDay.get(Calendar.YEAR);
			targetMonth = firstDay.get(Calendar.MONTH);
		}
		
		firstDay.set(Calendar.DATE, 1);
		// 달력출력시 시작공백
		
		int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK)-1; // 1일날짜의 요일(일1, 월2,...토6) - 1
		System.out.println(beginBlank+"beginBlank");
		
		// 출력되는 월의 마지막날짜
		int lastDate = firstDay.getActualMaximum(Calendar.DATE);
		System.out.println(lastDate+"lastDate");
		
		// 달력출력시 마지막 날짜 출력 후 공백 개수 -> 전체 셀의 개수가 7로 나누어 떨어져야 한다
		int endBlank = 0;
		
		if(((beginBlank+lastDate+endBlank)%7) != 0) {
			endBlank = 7 - ((beginBlank+lastDate)%7);
		}
		int totalCell = beginBlank+lastDate+endBlank;
		System.out.println(totalCell+"totalCell");
		System.out.println(endBlank+"endBlank");
		
		// 모델을 호출(DAO 타깃 월의 수입/지출 데이터)
		List<Cashbook> list = new CashbookDao().selectCashbookListByMonth(memberId, targetYear, targetMonth+1);
		
		List<Map<String,Object>> htList = new HashtagDao().selectWordCountByMonth(memberId, targetYear, targetMonth+1);
		System.out.println(htList.size());
		
		// 뷰에 값넘기기(request속성)
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("totalCell", totalCell);
		request.setAttribute("beginBlank", beginBlank);
		request.setAttribute("endBlank", endBlank);
		
		request.setAttribute("list", list);
		request.setAttribute("htList", htList);
		
		// 달력을 출력하는 뷰
		request.getRequestDispatcher("WEB-INF/view/calendar.jsp").forward(request, response);
		
	}


}
