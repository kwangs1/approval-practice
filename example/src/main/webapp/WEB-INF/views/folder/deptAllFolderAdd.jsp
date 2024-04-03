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
	<select id="deptApplid" onchange="setApplidValue(this)">
		<option value="" selected="selected">생성할 폴더를 선택 하십시오.</option>
		<option value="8010">기록물 등록대장</option>
		<option value="7000">단위과제</option>
	</select>
	<input type="hidden" name="applid" id="applid" value=""/><br><br>
	
	<input type="hidden" name="fldrname" id="fldrname"/>
	소유자 유형: <input type="hidden" name="ownertype" id="ownertype" value="1" />부서<br><br>
	폴더 유형: <input type="hidden" name="appltype" id="appltype"/>문서함(부서)<br><br>
	
	생산년도: <input type="text" name="year" id="year" readonly="readonly" style="border:none;"/><br><br>
	종료년도: <input type="text" name="endyear" id="endyear"/><br><br>
	
	<button type="button" onclick="insertBtn()">생성</button>
	<button type="button" onclick="javascript:window.close()">닫기</button>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(document).ready(function(){
	setYear();
	
	$('#deptApplid').change(function(){
		var SelectText = $(this).find("option:selected").text();
		
		$('#fldrname').val(SelectText);
		var applid = $(this).val();
		setApplType(applid);
	})
})

function setApplidValue(selectElement){
	document.getElementById('applid').value = selectElement.value;	
}

function setApplType(applid){
	var appltype = $('#appltype');
	if(applid === '8010'){
		appltype.val('1');
	}else{
		appltype.val('3');
	}
}

function setYear(){
	var today = new Date();
	var CurrYear = today.getFullYear();
	document.getElementById('year').value = CurrYear;
	document.getElementById('endyear').value = CurrYear;
}

function insertBtn(){
	var applid = $('#applid').val(); 
	var fldrname = $('#fldrname').val(); 
	var ownertype = $('#ownertype').val(); 
	var appltype = $('#appltype').val();
	var year = $('#year').val(); 
	var endyear = $('#endyear').val();
	
	var paramData = {
			applid :applid,
			fldrname :fldrname,
			ownertype :ownertype,
			appltype :appltype,
			year :year,
			endyear :endyear
	}
	
	$.ajax({
		type: 'post',
		url: '<c:url value="/folder/deptAllFolderAdd"/>',
		data: paramData,
		success: function(){
			var loading = document.getElementById('loading')
			loading.style.display = 'flex';
			
			setTimeout(function(){
				alert('등록되었습니다.');
				window.close();
				opener.location.reload();
			},5000)
		},
		error: function(xhr,error,status){
			console.log(xhr);
			console.log(error);
			console.log(status);
		}
	})
}
</script>
</body>
</html>