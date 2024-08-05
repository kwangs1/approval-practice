<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/transferInfo.css'/>"/>
</head>
<body>
<div class="container_box">			
	<div class="title_area">
		<h1>편철확인</h1>
	</div>
					
	<div class="pop_container">
		<table class="tbl_list horizon">
			<colgroup>
				<col style="width:120px;"/>
				<col style="width:*"/>
			</colgroup>
			<tbody>
				<tr>
					<th>예상이관년도</th>
					<td>
					<input type="text" name="transferyear" id="transferyear" maxlength="4"
						 value="<c:out value='${transferYear}'/>"
						 onkeydown="javascript:if (event.keyCode == 13) { doSubmit(); }" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<button onclick="doSubmit()">적용</button>
	<button onclick="javascript:self.close()">취소</button>				
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var fldrid = '<c:out value="${Info.fldrid}"/>';

function doSubmit(){
	var currentYear = new Date().getFullYear();
	var tranYear = $('#transferyear').val();
	
	if(!/^\d+$/.test(tranYear)){
		alert("숫자만 입력해주세요.");
		return false;
	}
	
	var n_tranYear = parseInt(tranYear,10);
	if(n_tranYear < currentYear){
		alert("금년 이후 숫자 4자리를 입력해주세요.");
		return;
	}
	
	var param = {
			fldrid: fldrid, 
			transferyear: $('#transferyear').val()
		}
	$.ajax({
		type:'post',
		url:'<c:url value="/folder/transferYear"/>',
		data: param,
		success: function(response){
			window.opener.location.reload();
	
			setTimeout(function(){
				if(window.opener && !window.opener.closed){
					window.opener.showAlertFromChild('편철확인이 되었습니다.');		
				}
			window.close();								
			},500)	
		},
		error: function(xhr,status,error){
			console.log(error);
			console.log(xhr.responseText);
		}
	});
}
</script>
</body>
</html>