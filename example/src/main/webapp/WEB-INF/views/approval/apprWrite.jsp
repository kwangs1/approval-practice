<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var = "path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<style>
body{background-color: #f0f0f0; font-family: Arial, sans-serif; font-size: 16px; padding: 20px; margin: 0;}
.btn-upload {
width: 150px; height: 30px; background: #fff; border: 1px solid rgb(77,77,77); border-radius: 10px;
font-weight: 500; cursor: pointer; display: flex; align-items: center; justify-content: center;
&:hover {background: rgb(77,77,77); color: #fff; }
}
.uploadResult ul li{list-style:none; padding-left:0px;}
.uploadResult ul{padding-left:0px;}
.vacation-period {margin-top: 20px; align-items: center;}
</style>
</head>
<body>
<input type="hidden" name="draftername" id="draftername" value="${user.name}" />
<input type="hidden" name="drafterid" id="drafterid" value="${user.id}" />
<input type="hidden" name="folderid" id="folderid"/>
<input type="hidden" name="foldername" id="foldername"/>
<input type="hidden" name="bizunitcd" id="bizunitcd"/>

<%@ include file="../participant/ParticipantWrite.jsp" %>
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>상신 중..</p>
</div>

<div class="vacation-period">
휴가 기간: <input type="date" name="startdate" id="startdate"/> ~
	<input type="date" name="enddate" id="enddate" />
<br><br>

<textarea name="title" id="title" rows="1" cols="50" placeholder="제목입력" autofocus="autofocus"></textarea>
<br><br>

<textarea name="content" id="content" rows="10" cols="80" placeholder="내용입력"></textarea>
<br><br>
</div>
	<div>
		<div>
			<label for="file" class="btn-upload">파일 업로드</label>		
			<input type="file" id="file" name="uploadFile" class="uploadFile" 
			multiple="multiple" style="display:none;"/>
		</div>
		<br>		
		<div class="uploadResult">
			<ul>
			</ul>
		</div>
	</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/UploadFile_.js'/>"></script>
<script>
var drafterdeptid = '<c:out value="${user.deptid}"/>';
var drafterdeptname = '<c:out value="${user.deptname}"/>';
var docregno = '<c:out value="${uInfo.abbreviation}"/>';
var id = '<c:out value="${user.id}"/>';
//새로고침 방지
window.addEventListener('keydown',function(e){
	//78: ctrl+N , 82: ctrl+R , 116: F5 | ctrlKey : window , metaKey : Mac
	if((e.ctrlKey == true || e.metaKey == true && (e.keyCode == 78 || e.keyCode == 82)) || (e.keyCode == 116))
	{
        e.preventDefault();
        e.stopPropagation();
	}
})

window.onload = function(){
	pop();
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

	window.addEventListener('message', function(e) {
		var data = e.data;
		var selectedApFolder = data.selectedApFolder;

		$('#folderid').val(selectedApFolder.fldrid);
		$('#foldername').val(selectedApFolder.fldrname);
		$('#bizunitcd').val(selectedApFolder.bizunitcd);
	});

	function Appr_Btn() {
		// FormData 객체 생성 
		//[첨부파일 데이터와 묶어서 서버로 보내기 위해서 결재 데이터도 같이 FormData에 넣어줌.]
		var formData = new FormData(); 
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;

	    // 기안 시 등록하는 필드 정보 추가
	    if(files.length > 0){
		    formData.append('draftername', $('#draftername').val());
		    formData.append('drafterid', $('#drafterid').val());
		    formData.append('title', $('#title').val());
		    formData.append('content', $('#content').val());
		    formData.append('startdate', $('#startdate').val());
		    formData.append('enddate', $('#enddate').val());
		    formData.append('drafterdeptid', drafterdeptid);
		    formData.append('drafterdeptname', drafterdeptname);
		    formData.append('docregno', docregno);
		    formData.append('folderid', $('#folderid').val());
		    formData.append('foldername', $('#foldername').val());
		    formData.append('bizunitcd', $('#bizunitcd').val());
		    // 파일 정보 추가
		    UploadFileAppend(formData);
		  	/*
		  	 * 파일 등록 시 ajax에서는 일반적인 body에서의 form태그 안 enctype="multipart/form-data" 의 값을 설정하기위해 
		  	 * processData, contentType 값을 flase로 설정
		  	*/
			$.ajax({
				type : "post",
				url : "${path}/approval/apprWrite",
				data : formData,        
		        processData: false,
		        contentType: false,
				success : function(response) {
					participant();
				},
				error : function(xhr, status, error) {
					console.log(xhr);
					console.log(status);
					console.log(error);
				}
			})	
	    }else{
	    	var apprData = {
	    			drafterdeptid: drafterdeptid,
	    			drafterdeptname: drafterdeptname,
	    			title: $('#title').val(),
	    			content: $('#content').val(),
	    			startdate: $('#startdate').val(),
	    			enddate: $('#enddate').val(),
	    			drafterid: $('#drafterid').val(),
	    			draftername: $('#draftername').val(),
	    			folderid: $('#folderid').val(),
	    			bizunitcd: $('#bizunitcd').val(),
	    			foldername: $('#foldername').val(),
	    			docregno: docregno
	    	}
			$.ajax({
				type : "post",
				url : "${path}/approval/apprWrite",
				data : apprData,        
				success : function(response) {
					participant();
				},
				error : function(xhr, status, error) {
					console.log(xhr);
					console.log(status);
					console.log(error);
				}
			})	
	    }
	}
</script>
</body>
</html>