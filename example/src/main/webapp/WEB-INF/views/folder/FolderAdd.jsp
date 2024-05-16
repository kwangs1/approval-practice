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

	<select id="deptApplid" onchange="setApplidValue(this)"  style="display:none">
		<option value="" selected="selected">생성할 폴더를 선택 하십시오.</option>
		<option value="7000">단위과제</option>
		<option value="8000">대장</option>
	</select>	
	<select id="userApplid" onchange="setApplidValue(this)" style="display:none">
		<option value="" selected="selected">선택 하십시오.</option>
		<option value="1000">결재</option>
		<option value="4000">발송</option>
		<option value="7010">접수</option>
	</select>
	<input type="hidden" name="applid" id="applid" value=""/><br><br>
	
	<input type="hidden" name="fldrname" id="fldrname"/>	
	<input type="hidden" name="ownerid" id="ownerid" />
	<input type="hidden" name="year" id="year" value="0000"/>
	<input type="hidden" name="endyear" id="endyear" value="9999"/>

소유자 유형:
	<input type="radio" name="ownertype"  id="ownertype_1"  value="1" onclick="showId()" checked="checked"/>문서함
	<input type="radio" name="ownertype"  id="ownertype_2"  value="2" onclick="showId()"/>결재함<br><br>

폴더 유형: 
	<input type="radio" name="appltype" id="appltype_1"  value="1"/>문서함
	<input type="radio" name="appltype" id="appltype_2"  value="2"/>결재함<br><br>
	
	<button type="button" onclick="insertBtn()">생성</button>
	<button type="button" onclick="javascript:window.close()">닫기</button>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(document).ready(function(){
	showId();
	
	$('#deptApplid').change(function(){
		var SelectText = $(this).find("option:selected").text();
		
		$('#fldrname').val(SelectText);
	})
	$('#userApplid').change(function(){
		var SelectText = $(this).find("option:selected").text();
		
		$('#fldrname').val(SelectText);
	})
})

function setApplidValue(selectElement){
	document.getElementById('applid').value = selectElement.value;	
}

function showId(){
	var OptionValue = document.querySelector('input[name="ownertype"]:checked').value;
	var ownerid = document.getElementById('ownerid')
	
	if(OptionValue == '1'){
		ownerid.value ='<c:out value="${user.deptid}"/>';
		document.getElementById('appltype_1').checked = true;
		$('#deptApplid').css('display','block');
		$('#userApplid').css('display','none');
	}else{
		ownerid.value ='<c:out value="${user.id}"/>';
		document.getElementById('appltype_2').checked = true;
		$('#deptApplid').css('display','none');
		$('#userApplid').css('display','block');
	}
}


function insertBtn(){
	var applid = $('#applid').val(); 
	var fldrname = $('#fldrname').val(); 
    var ownertype = $('input[name="ownertype"]:checked').val(); 
    var appltype = $('input[name="appltype"]:checked').val();
    var year = $('#year').val();
    var endyear = $('#endyear').val();
    var ownerid = $('#ownerid').val();
    
	var paramData = {
			applid :applid,
			fldrname :fldrname,
			ownertype :ownertype,
			appltype :appltype,
			ownerid :ownerid,
			year :year,
			endyear :endyear
	}
	
	$.ajax({
		type: 'post',
		url: '<c:url value="/folder/FolderAdd"/>',
		data: paramData,
		success: function(){
			var loading = document.getElementById('loading')
			loading.style.display = 'flex';
			
			setTimeout(function(){
				alert('등록되었습니다.');
				window.close();
				opener.location.reload();
			},2000)
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