<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<form method="post" action="${path}/user/login">
	id: <input type="text" name="id" autofocus/><br>
	pw: <input type="password" name="pw"/><br>
	
	<c:if test="${result == 0 }">
		<div class="login_warn"> ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
	</c:if>
	
	<button type="submit">로그인</button>
</form>
	<button onclick="join()">회원가입</button>
<script>
function join(){
	location.href="${path}/user/write"
}
</script>
</body>
</html>