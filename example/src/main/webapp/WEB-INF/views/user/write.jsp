<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
#checkF{
	display: none;
	color: red;
}
#checkS{
	display: none;
	color: green;
}
</style>
</head>
<body>
<form method="post" action="${path}/user/write">
	<input type="hidden" name="deptid" id="deptid"/>
	<input type="text" name="deptname" id="deptname" placeholder="부서명" class="deptInfo"/><br>
	<input type="text" name="id" id="userid" placeholder="아이디를 입력하세요." autofocus/><br>
	<p id="checkF">존재하는 아이디 입니다.</p>
	<p id="checkS">사용가능한 아이디 입니다.</p>
	<input type="password" name="pw" id="userpw" placeholder="비밀번호를 입력하세요."/><br>
	<input type="text" name="name" id="username" placeholder="이름을 입력하세요."/><br>
	<select name="pos">
	    <option value="">직위를 선택하세요.</option>
	    <c:forEach var="posItem" items="${pos}">
	        <option value="${posItem.posname}">${posItem.posname}</option>
	    </c:forEach>
	</select>
	
	<button type="submit">가입</button>
</form>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>

$('#userid').on('propertychange change keyup paste input',function(){
	var id = $('#userid').val();
	
    if (id.trim() === '') {
        $('#checkS').css('display', 'none');
        $('#checkF').css('display', 'none');
        return;
    }
	$.ajax({
		type: 'post',
		url: '${path}/user/idcheck',
		data: {id: id},
		success: function(result){
			if(result === 'fail'){
				$('#checkF').css('display','block');
				$('#checkS').css('display','none');
			}else{
				$('#checkS').css('display','block');
				$('#checkF').css('display','none');
			}
		},
		error: function(xhr,status,error){
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	})
})

window.addEventListener('message', function(e){
	var data = e.data;
	var deptname = data.deptname;
	var deptid = data.deptid;
	
	$('#deptname').val(deptname);
	$('#deptid').val(deptid);
});

$('.deptInfo').click(function(){
	window.open("<c:url value='/dept/joinUseDept'/>", 'deptLink','width=550, height=350');
});
</script>
</body>
</html>