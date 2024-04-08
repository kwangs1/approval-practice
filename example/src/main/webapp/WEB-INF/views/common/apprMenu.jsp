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
<a href="apprWaitList?id=${user.id}">결재대기</a> |
<a href="SanctnProgrsList?id=${user.id}">결재진행</a>|
<a href="docFrame?drafterdeptid=${user.deptid}">문서함</a>


<script>
function approval_pop(){
	window.open("${path}/approval/apprWrite","approval","width=1024px, height=768");
}
</script>
</body>
</html>