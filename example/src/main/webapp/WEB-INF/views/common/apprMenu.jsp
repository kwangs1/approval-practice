<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<a href="javascript:approval_pop()">기안하기</a> | 
<a href="javascript:loadApprFrameList()">결재함</a> |
<a href="javascript:loadDocFrameList()">문서함</a>


<script src="<c:url value='/resources/js/pagingCookie.js'/>"></script>
<script>
function approval_pop(){
	window.open("<c:url value='/approval/apprWrite'/>","approval","width=1024px, height=768");
}

function loadDocFrameList(){
	var getLastDocUrl = getCookie("lastDocUrl");
	if(getLastDocUrl !== null){
		var docUrl = decodeURIComponent(getLastDocUrl);
		window.location.href = docUrl;
	}else{
		url = '<c:url value="docFrame?drafterdeptid=${user.deptid}"/>';
		window.location.href = url;
	}
}
function loadApprFrameList(){
	var g_lastApprUrl = getCookie("lastApprUrl");
	if(g_lastApprUrl !== null){
		var apprUrl = decodeURIComponent(g_lastApprUrl);
		window.location.href = apprUrl
	}else{
		url = '<c:url value="/approval/apprFrame?id=${userId}"/>';
		window.location.href = url;
	}
}
</script>
</body>
</html>