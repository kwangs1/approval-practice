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

<form name="insertF" method="post">
<input type="hidden" name="parfldrid" value="${info.fldrid}"/>
<input type="hidden" name="parfldrname" value="${info.fldrname }"/>
<input type="hidden" name="fldrdepth" value="${subDepth}"/>
<input type="hidden" name="ownerid" id="ownerid" value="${user.deptid}"/>


	<select id="deptApplid" onchange="setApplidValue(this)">
		<option value="" selected="selected">생성할 폴더를 선택 하십시오.</option>
		<option value="8010">기록물 등록대장</option>
	</select>
	<input type="hidden" name="applid" id="applid" value=""/><br><br>
	
	<input type="hidden" name="fldrname" id="fldrname"/>
	소유자 유형: <input type="hidden" name="ownertype" id="ownertype" value="1" />부서<br><br>
	폴더 유형: <input type="hidden" name="appltype" id="appltype" value="1"/>문서함(부서)<br><br>
	
	생산년도: <input type="text" name="year" id="year" readonly="readonly" style="border:none;" value="0000"/><br><br>
	종료년도: <input type="text" name="endyear" id="endyear" readonly="readonly" style="border:none;" value="9999"/><br><br>
	
	<button type="button" onclick="insertBtn()">생성</button>
	<button type="button" onclick="javascript:window.close()">닫기</button>
</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(document).ready(function(){
	$('#deptApplid').change(function(){
		var SelectText = $(this).find("option:selected").text();
		
		$('#fldrname').val(SelectText);
	})
})

function setApplidValue(selectElement){
	document.getElementById('applid').value = selectElement.value;	
}

function insertBtn(){
	document.insertF.action = '<c:url value="/folder/subFolderAdd"/>';
	document.insertF.submit();
	
	alert("폴더가 등록되었습니다.");

}
</script>
</body>
</html>