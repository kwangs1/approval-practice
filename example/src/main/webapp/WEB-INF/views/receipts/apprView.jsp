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
	<button onClick="Appr_Btn();">상신</button>
</form> 

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function Appr_Btn(){
	var Obj = document.getElementById("frmObj");
	Obj.action = "${path}/receipts/apprView";
	
	// submit 이벤트에 대한 리스너 등록
	Obj.addEventListener('submit', function (event) {
	    event.preventDefault(); // 기존의 submit 동작 방지
		
	    // 서버에 폼 데이터 전송 (submit)
	    var xhr = new XMLHttpRequest();
	    xhr.open('POST', Obj.action , true);
	    xhr.onload = function () {
	        if (xhr.status === 200) {
	            // submit이 완료된 후 participant 함수 호출
	            participant();
	        }
	    };
	    // submit 호출
	    xhr.send();
	});
}
</script>
</body>
</html>