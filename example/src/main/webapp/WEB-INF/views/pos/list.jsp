<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<style>
table { border: 1px solid #000; }
th { border: 1px solid #000; }
td { border: 1px solid #000; }
</style>
<body>
<a href="javascript:posWrite()">등록</a>
<a href="javascript:window.close()">닫기</a>
<table>
	<thead>
	<tr>
		<th>직위명</th>
		<th>결재순번</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="list" items="${list}">
		<tr>
			<td>${list.posname}</td>
			<td>${list.seq}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<script>
function posWrite(){
	url = '<c:url value="/pos/write"/>';
	window.open('<c:url value="/pos/write"/>','pos','width=220px, height=135px');
}
</script>
</body>
</html>