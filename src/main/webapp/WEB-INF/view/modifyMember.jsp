<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifyMember</title>
</head>
<body>
	<h1>회원정보수정</h1>
	<form action="${pageContext.request.contextPath}/modifyMember" method = "post">
	<table>
		<tr>
			<th>기존비밀번호</th>
			<td><input type = "password" name = "memberPw"></td>
		</tr>
		<tr>
			<th>변경할비밀번호</th>
			<td><input type = "password" name = "newPw"></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>
			<td><input type = "password" name = "newCkPw"></td>
		</tr>
	</table>
	<button type = "submit">회원탈퇴</button>
	</form>
</body>
</html>