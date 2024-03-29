<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<form name="writeForm" method="post">
	직위명 <input type="text" name="posname" autofocus="autofocus"/>
	순번   <input type="text" name="seq"/>
	<br><br>
	<a href="javascript:write()">등록</a>
</form>

<script>
function write(){
	document.writeForm.action = '<c:url value="/pos/write"/>';
	document.writeForm.submit();
	
	alert("직위가 등록되었습니다.");
}
</script>
</body>
</html>