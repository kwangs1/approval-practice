<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

	<button onclick="pop()">유저 목록</button>
<form method="post" id="frmObj">
<!-- 
	<input type="hidden" id="id" name="${paticipant.id}" />
	<input type="hidden" id="pos" name="${paticipant.pos}" />
	<input type="text" id="name" name="${paticipant.name}" />
	-->
	<!-- 동적으로 생성 될 input box 위치 -->
		<div id="inputs"></div>
	<button type="submit">상신</button>	
</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
window.addEventListener('message',function(e){
	var data = e.data;
	//var id = data.id;
	//var name = data.name;
	//var pos = data.pos;
	var users  = data.users;
	console.log(data.users);
	<%--
	$('#id').val(users[0].id);
	$('#name').val(users[0].name);
	$('#pos').val(users[0].pos);--%>
	
	 var inputs = $('#inputs');
	 inputs.empty();
	
	  for (var i = 0; i < users.length; i++) {
	      inputs.append('<input type="hidden" name="id" value="' + users[i].id + '" />');
	      inputs.append('<input type="text" name="name" value="' + users[i].name + '" />');
	      inputs.append('<input type="hidden" name="pos" value="' + users[i].pos + '" />');
	  }
});

function pop(){
	window.open("${path}/user/list","pop","width=300, height=300");
}
</script>
</body>
</html>