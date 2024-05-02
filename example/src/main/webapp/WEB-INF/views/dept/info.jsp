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
<a href="${path}/dept/subDept?deptid=${info.deptid}">부서 추가</a> | 
<a href="${path}/dept/list">뒤로가기</a>
<div class="post">
	<p class="postA">부서명 | ${info.deptname} </p>
	<p class="postA">상위부서/부서명 | ${info.org_deptname} </p>
	<c:choose>
		<c:when test="${info.parid eq null}">
			<p class="postA">상위부서 | 없음</p>
		</c:when>
		<c:otherwise>
			<p class="postA">상위부서 | ${info.parid} </p>
		</c:otherwise>
	</c:choose>
	
	<p class="postA">부서약어 | ${info.abbreviation} </p>
	<p class="postA">발신명의 | ${info.sendername} </p>
</div>
</body>
</html>