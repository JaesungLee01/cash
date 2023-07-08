<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendarOne</title>
</head>
<body>
	<h1>${targetYear}년 ${targetMonth+1}월 ${targetDay}일</h1>
	<h1>지출상세보기</h1>
	<table border="1">
		<tr>
			<th>수입/지출</th>
			<th>금액</th>
			<th>메모</th>
			<th>날짜</th>
		</tr>
		<c:forEach var="c" items="${list}">
		<tr>
			<td>${c.category}</td>
			<td>${c.price}</td>
			<td>${c.memo}</td>
			<td>${c.cashbookDate}</td>
		</tr>
		
		</c:forEach>
	</table>
	<a href="${pageContext.request.contextPath}/addCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDay=${targetDay}">가계부 추가</a>
</body>
</html>