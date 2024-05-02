<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<a href="${path}/dept/write">부서등록</a> | 
<a href="${path}/">뒤로가기</a></br>
<table>
	<thead>
	<tr>
		<th>부서명 </th>
		<th>상위부서/부서명</th>
	</tr>
	</thead>
	<tbody>
		<c:forEach var="list" items="${list}" >
		<tr>
			<td><a href="${path}/dept/info?deptid=${list.deptid}">${list.deptname}</a></td>
			<td>${list.org_deptname}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>