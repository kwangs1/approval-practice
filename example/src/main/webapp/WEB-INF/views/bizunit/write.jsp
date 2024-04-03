<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<body>
<div class="loading" id="loading" style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>등록 중..</p>
</div>

담당부서: <input type="text" name="procdeptname" id="procdeptname" value="${user.deptname}"/><br><br>
담당자: <input type="text" name="bizunitchargeid" id= "bizunitchargeid" value="${user.name }"/>
<input type="hidden" name="procdeptid" id="procdeptid" value="${user.deptid}"/><br><br>

단위과제코드: <input type="text" name="bizunitcd" id="bizunitcd" placeholder="형식 ZZ로 시작, 숫자6자리" autofocus="autofocus"><br><br>
보존기간: <select name="keepperiod" id="keepperiod">
			<option value="">단위과제 보존기간 선택</option>
			<option value="01">1년</option>
			<option value="03">3년</option>
			<option value="05">5년</option>
			<option value="10">10년</option>
			<option value="20">20년</option>
			<option value="30">준영구</option>
			<option value="40">영구</option>
		</select><br><br>
단위과제명: <input type="text" name="bizunitname" id="bizunitname"><br><br>
<textarea rows="5" cols="70" name="bizunitdesc" placeholder="단위과제에 대한 설명" id="bizunitdesc"></textarea>
<br><br>
<button type="button" onclick="add()">등록</button>
<button type="button" onclick="window.close()">닫기</button>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function add(){
	var procdeptname = $('#procdeptname').val();
	var bizunitchargeid = $('#bizunitchargeid').val();
	var procdeptid = $('#procdeptid').val();
	var bizunitcd = $('#bizunitcd').val();
	var keepperiod = $('#keepperiod').val();
	var bizunitname = $('#bizunitname').val();
	var bizunitdesc = $('#bizunitdesc').val();

	var paramData = {
			procdeptname : procdeptname,
			bizunitchargeid : bizunitchargeid,
			procdeptid : procdeptid,
			bizunitcd : bizunitcd,
			keepperiod : keepperiod,
			bizunitname : bizunitname,
			bizunitdesc : bizunitdesc
	}
	
	$.ajax({
		type:'post',
		url:'<c:url value="/bizunit/write"/>',
		data: paramData,
		success: function(){
			var loading = document.getElementById('loading')
			loading.style.display = 'flex';
			
			setTimeout(function(){
				alert('등록되었습니다.');
				window.close();
				opener.location.reload();
			},3000)
		},
		error: function(xhr,status,error){
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	})
}
</script>
</body>
</html>