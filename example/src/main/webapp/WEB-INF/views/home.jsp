<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>Home</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<c:if test ="${user == null}">
<h1>
	Welcome to world!
</h1>

<a href="${path}/memo/list">메모</a>
<a href="${path}/user/write">회원가입</a>
<a href="${path}/user/login">로그인</a>
</c:if>

<c:if test ="${user != null}">
<h1>
	Welcome to world!  ${user.name}
</h1>

<a href="${path}/memo/list">메모</a>
<a href="${path}/approval/list">결재</a>
<a href="${path}/user/logout">로그아웃</a>
</c:if>

</body>
</html>
