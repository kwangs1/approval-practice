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
#receivers{border:none; pointer-events:none;}
.sender_info{pointer-events:none;}
</style>
</head>
<body>
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

<div class="receivers_info"></div>
<div class="sender_info"></div>
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

<input type="hidden" name="draftername" id="draftername" value="${user.name}" />
<input type="hidden" name="drafterid" id="drafterid" value="${user.id}" />
<input type="hidden" name="folderid" id="folderid"/>
<input type="hidden" name="foldername" id="foldername"/>
<input type="hidden" name="bizunitcd" id="bizunitcd"/>
<input type="hidden" name="docattr" id="docattr"/>
<input type="hidden" name="orgdraftdeptid" id="orgdraftdeptid"/>
<input type="hidden" name="sendername" id="sendername"/>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/UploadFile_.js'/>"></script>
<script src="<c:url value='/resources/js/ApprCookie.js'/>"></script>
<script>
var drafterdeptid = '<c:out value="${user.deptid}"/>';
var drafterdeptname = '<c:out value="${user.deptname}"/>';
var docregno = '<c:out value="${uInfo.abbreviation}"/>';
var id = '<c:out value="${user.id}"/>';
var SendID = '';
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

var selectedDept = [];
window.addEventListener('message', function(e) {
	var data = e.data;
	var selectedApFolder = data.selectedApFolder;
	var checkedValues = data.checkedValues;
	selectedDept = data.selectedDept; 
	var selectedValue = data.selectedValue;

	$('#folderid').val(selectedApFolder.fldrid);
	$('#foldername').val(selectedApFolder.fldrname);
	$('#bizunitcd').val(selectedApFolder.bizunitcd);
	$('#docattr').val(checkedValues);
	$('#sendername').val(selectedValue.sendername);
	
	var useDiv = $('.receivers_info');
	useDiv.empty();
	
	var resultString = '수신처: ';
	for(var i =0; i < selectedDept.length; i++){
		resultString += selectedDept[i].sendername;
		if(i < selectedDept.length -1){
			resultString += ',';
		}
	}
	
	var Container = $('<div class="container">');
	for(var i =0; i < selectedDept.length; i++){
		Container.append('<input type="hidden" name="receivers_' + i + '" id="receivers" value="' + selectedDept[i].sendername + '"/>');
	}
	if($('#docattr').val() === '1'){
		$('.sender_info').html('발신처: '+$('#sendername').val());
		Container.append('<p>' +resultString+ '</p>')
		useDiv.append(Container);
	}else{
		$('.sender_info').html('');
		useDiv.html('');
	}
	
	saveCookie(checkedValues);
});
	

	function Appr_Btn() {
		// FormData 객체 생성 
		//[첨부파일 데이터와 묶어서 서버로 보내기 위해서 결재 데이터도 같이 FormData에 넣어줌.]
		var formData = new FormData(); 
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		var doc_Receivers = [];
		
		if(selectedDept && selectedDept.length > 0 && $('#docattr').val() === '1'){
			for(var i=0; i < selectedDept.length; i++){
				doc_Receivers.push(selectedDept[i].sendername);
			}
			formData.append('receivers',doc_Receivers);
		}
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
		formData.append('docattr',$('#docattr').val());
		formData.append('sendername',$('#sendername').val());
		formData.append('orgdraftdeptid',drafterdeptid);
		if(files.length > 0){
		    UploadFileAppend(formData);
		}
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
				deleteCookie('docattr');
			},
			error : function(xhr, status, error) {
				console.log(xhr);
				console.log(status);
				console.log(error);
			}
		})	
	}
</script>
</body>
</html>