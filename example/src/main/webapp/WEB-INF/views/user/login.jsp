<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${path}/resources/css/login.css" />
</head>
<body>
    <section class="login-form">
        <h1>Login TEST</h1>   
		<form method="post" action="${path}/user/login">
            <div class="int-area">
                <input type="text" name="id" id="id" autocomplete="off" required autofocus="autofocus">
                <label for="id">USER ID</label>
            </div>
            <div class="int-area">
                <input type="password" name="pw" id="pw" autocomplete="off" required>
                <label for="pw">PASSWORD</label>
            </div>
            <c:if test="${result == 0 }">
				<div style="color:red;"> ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
			</c:if>
            <div class="btn-area">
                <button id="btn" type="submit">LOGIN</button>
                
            </div>
        </form>
        <div class="caption">
            <a href="">Forgot Password?</a>
            <a href="javascript:join()">회원가입</a>
        </div>
    </section> 

<script>
function join(){
	location.href="${path}/user/write"
}
</script>
</body>
</html>