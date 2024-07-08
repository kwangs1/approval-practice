<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
</head>
<body>
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>등록 중..</p>
</div>
단위과제명: <input type="text" value="${bInfo.bizunitname}"/><br><br>
기록물철명: <input type="text" name="fldrname" id="fldrname" autofocus="autofocus"/><br><br>
단위과제코드: <input type="text" value="${bInfo.bizunitcd}" id="bizunitcd"/><br><br>

처리과: <input type="text" value="${bInfo.procdeptid}"  id="procdeptid"/><br><br>

<input type="text" name="fldrmanagerid" id="fldrmanagerid" />
담당자: <a href="#" class="userLink" style="text-decoration: none;">🕵🏻</a>
		<input type="text" name="fldrmanagername" id="fldrmanagername" readonly="readonly" class="userLink"/><br><br>

보존기간: <select value="${bInfo.keepperiod}" id="keepperiod">
			<option value="">보존기간 선택</option>
			<option value="01">1년</option>
			<option value="03">3년</option>
			<option value="05">5년</option>
			<option value="10">10년</option>
			<option value="20">20년</option>
			<option value="30">준영구</option>
			<option value="40">영구</option>
		</select><br><br>
생산년도: <input type="text" name="year" id="year" readonly="readonly" style="border:none;"/><br>
종료년도: <input type="text" name="endyear" id="endyear"/><br><br>

<button type="button" onclick="addApprFolder()">등록</button>
<button type="button" onclick="window.close()">닫기</button>



<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(document).ready(function(){
	var keep = '${bInfo.keepperiod}';
	
	$('option').each(function(i, obj){
		if(keep === $(obj).val()){
			$(obj).attr('selected','selected')
		}
	})
	setYear();
})

function setYear(){
	var today = new Date();
	var CurrYear = today.getFullYear();
	document.getElementById('year').value = CurrYear;
	document.getElementById('endyear').value = CurrYear;
}

$(document).on('click','.userLink',function(){
	window.open("<c:url value='/dept/FolderUseList'/>", 'deptLink','width=550, height=350');
})

window.addEventListener('message', function(e){
	var data = e.data;
	var name = data.name;
	var id = data.id;
	
	$('#fldrmanagername').val(name);
	$('#fldrmanagerid').val(id);
});

function addApprFolder(){
	var year = $('#year').val();
	var endyear = $('#endyear').val();
	var fldrname = $('#fldrname').val();
	var parfldrid = '<c:out value="${info.fldrid}"/>';
	var parfldrname = '<c:out value="${info.fldrname}"/>';
	var appltype = '<c:out value="${info.appltype}"/>';
	var ownertype = '<c:out value="${info.ownertype}"/>';
	var ownerid = '<c:out value="${info.ownerid}"/>';
	var fldrdepth = '<c:out value="${depth}"/>';
	var fldrmanagerid = $('#fldrmanagerid').val();
	var fldrmanagername = $('#fldrmanagername').val();
	
	var paramData = {
			year :year,
			endyear :endyear,
			fldrname :fldrname,
			parfldrid :parfldrid,
			parfldrname :parfldrname,
			appltype :appltype,
			ownertype :ownertype,
			ownerid :ownerid,
			fldrdepth :fldrdepth,
			fldrmanagerid: fldrmanagerid,
			fldrmanagername: fldrmanagername,
			bizunitcd: $('#bizunitcd').val(),
			procdeptid: $('#procdeptid').val(),
			keepperiod: $('#keepperiod').val()
	}
	
	$.ajax({
		type: 'post',
		url: '<c:url value="/folder/folderAddAndApprF"/>',
		data: paramData,
		success: function(){
			var loading = document.getElementById('loading')
			loading.style.display = 'flex';
			
			setTimeout(function(){
				alert('등록되었습니다.');
				window.close();
				opener.location.href ='<c:url value="/folder/list"/>'
			},3000)
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