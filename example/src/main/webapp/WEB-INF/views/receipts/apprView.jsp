<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var = "path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<%@ include file="../receipts/write.jsp" %>
<form method="post" id="frmObj">
	<input type="hidden" name="name" value="${user.name}" />
	<input type="hidden" name="id" value="${user.id}" />
	품명: <input type="text" name="productname" />
	<a href="javascript:void(0)" onclick="btn()">상신</a>
</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function btn(){
	var Obj = document.getElementById("frmObj");
	Obj.action = "${path}/receipts/apprView";
	
	Obj.submit();
	participant();
}
</script>
</body>
</html>