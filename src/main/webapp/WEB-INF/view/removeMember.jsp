<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>removeMember</title>
</head>
<body>
	<h1>회원탈퇴</h1>
	<form action="${pageContext.request.contextPath}/removeMember" method = "post">
	<table>
		<tr>
			<th>아이디</th>
			<td>${member.memberId}</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type = "password" name = "memberPw"></td>
		</tr>
	</table>
	<button type = "submit">회원탈퇴</button>
	</form>
</body>
</html>