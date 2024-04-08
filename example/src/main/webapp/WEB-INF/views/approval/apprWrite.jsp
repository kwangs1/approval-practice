<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var = "path" value="${pageContext.request.contextPath}" />
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
<%@ include file="../participant/ParticipantWrite.jsp" %>
<hr>

	<button onClick="Appr_Btn();">상신</button>
	<button onClick="window.close()">닫기</button>
<hr>
<input type="hidden" name="draftername" id="draftername" value="${user.name}" />
<input type="hidden" name="drafterid" id="drafterid" value="${user.id}" />

<input type="hidden" name="folderid" id="folderid"/>
<input type="hidden" name="bizunitcd" id="bizunitcd"/>
<body>

휴가 기간: <input type="date" name="startdate" id="startdate"/> ~
	<input type="date" name="enddate" id="enddate" />
<br><br>

<textarea name="title" id="title" rows="1" cols="50" placeholder="제목입력" autofocus="autofocus"></textarea>
<br><br>

<textarea name="content" id="content" rows="10" cols="80" placeholder="내용입력"></textarea>
<br><br>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var drafterdeptid = '<c:out value="${user.deptid}"/>';
var drafterdeptname = '<c:out value="${user.deptname}"/>';
var docregno = '<c:out value="${uInfo.abbreviation}"/>';

window.onload = function(){
	var startDay = new Date();
	var endDay = new Date(startDay);
	endDay.setDate(startDay.getDate() +7);
	
	startDay = startDay.toISOString().slice(0,10);
	endDay = endDay.toISOString().slice(0,10);
	
	start = document.getElementById('startdate');
	end = document.getElementById('enddate');
	
	start.value = startDay;
	end.value = endDay;
}

function Appr_Btn(){
	var draftername = $('#draftername').val();
	var drafterid = $('#drafterid').val();
	var title = $('#title').val();
	var content = $('#content').val();
	var startdate = $('#startdate').val();
	var enddate = $('#enddate').val();
	var folderid = $('#folderid').val();
	var bizunitcd = $('#bizunitcd').val();
	
	var apprData = {
		draftername : draftername,
		drafterid : drafterid,
		title : title,
		content : content,
		startdate : startdate,
		enddate : enddate,
		drafterdeptid: drafterdeptid,
		drafterdeptname : drafterdeptname,
		docregno :docregno,
		folderid :folderid,
		bizunitcd :bizunitcd
	}
	
	$.ajax({
		type: "post",
		url: "${path}/approval/apprWrite",
		data: apprData,
		success: function(response){
			participant();
		},
        error: function (xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
	})
}

window.addEventListener('message', function(e){
	var data = e.data;
	var folderid = data.fldrid;
	var bizunitcd = data.bizunitcd;
	
	$('#folderid').val(folderid);
	$('#bizunitcd').val(bizunitcd);
});

</script>
</body>
</html>