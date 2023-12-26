<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<c:forEach var="user" items="${user}">
	<a href="#" class="userLink" data-id="${user.id}" data-name="${user.name}" data-pos="${user.pos}">
		${user.name} [${user.pos}]</a>
</c:forEach>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>

$('a.userLink').on('click',function(e){
	e.preventDefault();
	
	var id = $(this).data('id');
	var name = $(this).data('name');
	var pos = $(this).data('pos');
	
	window.opener.postMessage({
		id : id, name : name, pos : pos},'*'
	);
	window.close();
})
</script>
</body>
</html>