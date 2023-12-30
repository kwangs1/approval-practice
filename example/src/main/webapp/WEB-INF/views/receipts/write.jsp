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
	<!-- 동적으로 생성 될 input box 위치 -->
		<div id="inputs"></div>
	<a href="javascript:void(0);" onclick="approval();">상신</a>	

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
window.addEventListener('message',function(e){
	var data = e.data;
	var users  = data.users;
	console.log(data.users);
	
	 var inputs = $('#inputs');
	 inputs.empty();
	
	 for (var i = 0; i < users.length; i++) {
	   inputs.append('<input type="hidden" id="' + users[i].id + '"   value="' + users[i].id + '" />');
	   inputs.append('<input type="text" name="' + users[i].name + '"   value="' + users[i].name + '" />');
	   inputs.append('<input type="hidden" pos="' + users[i].pos + '"  value="' + users[i].pos + '" />');
	}
});

function pop(){
	window.open("${path}/user/list","pop","width=300, height=300");
}
function approval(){

    var formData = $('#inputs :input').serializeArray();
    
	$.ajax({
	    type: 'post',
	    url: '${path}/receipts/write',
	    data: JSON.stringify(formData),
	    contentType: 'application/json',
	    success: function(response){
	        console.log('Ajax 요청: '+response);
	    },
	    error: function(xhr, status, error){
	        console.log(xhr);
	        console.log(status);
	        console.log(error);
	    }
	});
}
</script>
</body>
</html>