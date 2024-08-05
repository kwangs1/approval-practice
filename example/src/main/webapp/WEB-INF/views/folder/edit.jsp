<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<body>
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>수정 중..</p>
</div>
<input type="hidden" name="fldrmanagerid" id="fldrmanagerid" />
	<button onclick="editBtn()">수정</button>
   <main>
        <section class="additional-info">
			<li>기록물철제목: <input type="text" name="fldrname" id="fldrname" value="${Info.fldrname}"/></li>
			<li>단위과제코드: ${Info.bizunitcd}</li>
			<li>처리과: ${Info.procdeptid}</li>
			
			<li>기록물철담당자:		
				<a href="#" class="userLink" style="text-decoration: none;">🕵🏻</a>
				<input type="text" id="fldrmanagername" name="fldrmanagername" readonly="readonly"
				 class="userLink" value="${Info.fldrmanagername}"/>
			</li>
			
			<li>생산년도: ${Info.prodyear}</li>
			<li>종료년도: <input type="text" name="endyear" id="endyear" value="${Info.endyear}"/></li>
			
			<li>보존기간:<select class="option" name="keepingperiod" id="keepingperiod">
				<option value="01">1년</option>
				<option value="03">3년</option>
				<option value="05">5년</option>
				<option value="10">10년</option>
				<option value="20">20년</option>
				<option value="30">준영구</option>
				<option value="40">영구</option>
			</select></li>
        </section>
    </main>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var fldrid = '<c:out value="${Info.fldrid}"/>';
$(document).ready(function(){
	var keepingperiod = '<c:out value="${Info.keepingperiod}"/>';
	$("option").each(function(i,obj){
		if(keepingperiod === $(obj).val()){
			$(obj).attr("selected", "selected");
		}
	});
});

window.addEventListener('message', function(e){
	var data = e.data;
	var name = data.name;
	var userid = data.userid;
	
	$('#fldrmanagername').val(name);
	$('#fldrmanagerid').val(userid);
});

$(document).on('click','.userLink',function(){
	window.open("<c:url value='/dept/FolderUseList'/>", 'deptLink','width=550, height=350');
})

function editBtn(){
	var loading = document.getElementById('loading')
	loading.style.display = 'flex';
	
	var paramData ={
		fldrid : fldrid,
		fldrmanagerid : $('#fldrmanagerid').val(),
		fldrmanagername : $('#fldrmanagername').val(),
		endyear : $('#endyear').val(),
		keepingperiod : $('#keepingperiod').val(),
		fldrname : $('#fldrname').val()
	}
	$.ajax({
		type: 'post',
		url: '<c:url value="/folder/edit"/>',
		data: paramData,
		success: function(response){
			console.log(response);
			setTimeout(function(){ 
				alert('기록물철의 정보가 수정되었습니다.');
				window.close();
				window.opener.location.reload();
			},1500);
		},
		error: function(xhr,status,error){
			consloe.log(error);
			console.log(xhr.responseText);
		}
	});
}
</script>
</body>
</html>