<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addCashbook</title>
</head>
<body>
	<h1>${targetYear}년 ${targetMonth+1}월 ${targetDay}일</h1>
	 <h1>가계부 추가</h1>
    <form action="${pageContext.request.contextPath}/addCashbook" method="post">
    <input type="hidden" name="targetYear" value="${targetYear}">
    <input type="hidden" name="targetMonth" value="${targetMonth}">
    <input type="hidden" name="targetDay" value="${targetDay}">
		<table border="1">
			<tr>
				<th>아이디</th>
				<td>${memberId}</td>
			</tr>
			<tr>
				<th>수입/지출</th>
				<td>
					<select name="category">
						<option value="수입">수입</option>
						<option value="지출">지출</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>날짜</th>
				<td>
					${cashbookDate}
					<input type="hidden" name="cashbookDate" value="${cashbookDate}">
				</td>
			</tr>
			<tr>
				<th>금액</th>
				<td><input type="number" name="price"></td>
			</tr>
			<tr>
				<th>메모</th>
				<td><textarea name="memo" cols="50" rows="5"></textarea></td>
			</tr>
		</table>
		<button type="submit">추가</button>
    </form>
</body>
</html>