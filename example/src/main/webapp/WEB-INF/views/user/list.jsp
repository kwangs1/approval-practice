<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
body {display: flex;}
#userList {flex: 1; border-right: 1px solid #ccc; padding-right: 10px; margin-right: 10px;}
#selectedUsers {flex: 1; padding-left: 10px;}
#selectedUsers p {margin-bottom: 10px;}
#userList a { display: block; margin-bottom: 10px;}
#confirmButton {margin-top: auto; /* 화면 아래로 내리기 */}
.userDiv {display: flex; align-items: center;}/* 위,아래 이동 및 삭제 버튼 */
</style>
</head>
<body>
<div id="userList">
	<c:forEach var="user" items="${user}" varStatus="loop">
		<a href="#" class="userLink" data-id="${user.id}" data-name="${user.name}" data-pos="${user.pos}">
			${user.name} [${user.pos}]</a>
	</c:forEach>
</div>
<div id="selectedUsers"></div>
<a href="javascript:void(0)" id="confirmButton" onclick="confirmSelection()">확인</a>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${path}/resources/js/sendFlow.js"></script>
<script>

</script>
</body>
</html>