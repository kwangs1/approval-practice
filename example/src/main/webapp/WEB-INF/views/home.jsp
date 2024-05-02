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
<h1>
	Welcome to world!  ${user.name}
</h1>

<a href="${path}/memo/list">메모</a> |
<a href="javascript:loadApprFrameList()">전자문서</a> |
<a href="${path}/dept/list">부서</a> |
<a href="javascript:pos()">직위</a> |
<a href="javascript:bizunit()">단위과제</a> |
<a href="javascript:folder()">폴더</a> |
<a href="${path}/user/logout">로그아웃</a>

<script src="<c:url value='/resources/js/pagingCookie.js'/>"></script>
<script>
function pos(){
	url = '<c:url value="/pos/list"/>';
	window.open(url,'pos','width=400px, height=350px');
}

function bizunit(){
	url = '<c:url value="/bizunit/list"/>';
	window.open(url,'pos','width=1024px, height=768px');
}

function folder(){
	url = '<c:url value="/folder/list"/>';
	window.open(url,'folder','width=500px, height=500px');
}

function loadApprFrameList(){
	var g_lastApprUrl = getCookie("lastApprUrl");
	if(g_lastApprUrl !== null){
		var apprUrl = decodeURIComponent(g_lastApprUrl);
		window.location.href = apprUrl
	}else{
		url = '<c:url value="approval/apprFrame?id=${userId}"/>';
		window.location.href = url;
	}
}
</script>
</body>
</html>
