<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbookListBytag</title>
</head>
<body>
	<h1>Tag List</h1>
	<table border="1">
		<tr>
			<th>번호</th>
			<th>수입/지출</th>
			<th>가격</th>
			<th>메모</th>
		
		</tr>
	<c:forEach var="c" items="${list}">
		<tr>
			<td>${c.cashbookNo}</td>
			<td>${c.category}</td>
			<td>${c.price}</td>
			<td>${c.memo}</td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>