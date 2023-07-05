<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberOne.jsp</title>
</head>
<body>
	<h1>회원상세보기</h1>
	<table>
		<tr>
			<th>아이디</th>
			<td>${member.memberId}</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>******</td>
		</tr>
		<tr>
			<th>수정날짜</th>
			<td>${member.updatedate}</td>
		</tr>
		<tr>
			<th>생성날짜</th>
			<td>${member.createdate}</td>
		</tr>
	</table>
	<a href="${pageContext.request.contextPath}/modifyMember">회원정보수정</a>
	<a href="${pageContext.request.contextPath}/removeMember">회원탈퇴</a>
</body>
</html>