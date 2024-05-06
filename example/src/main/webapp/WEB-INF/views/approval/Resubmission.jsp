<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
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
ul li{list-style:none; padding-left:0px;}
ul{padding-left:0px;}
.vacation-period {margin-top: 20px; align-items: center;}
</style>
</head>
<body>
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>상신 중..</p>
</div>
<%@ include file="../participant/ParticipantWrite.jsp" %>
<body>
<input type="hidden" name="folderid" id="folderid" value="${info.folderid}"/>
<input type="hidden" name="folderid" id="foldername" value="${info.foldername}"/>
<input type="hidden" name="bizunitcd" id="bizunitcd" value="${info.bizunitcd}"/>
<input type="hidden" name="attachcnt" id="attachcnt" value="${info.attachcnt}"/>
	<div class="vacation-period">
		휴가 기간: <input type="date" name="startdate" id="startdate" value="${info.startdate}"/> ~
			<input type="date" name="enddate" id="enddate" value="${info.enddate}"/>
		<br><br>	
		<textarea name="title" id="title" rows="1" cols="50" placeholder="제목입력" autofocus="autofocus">${info.title }</textarea>
		<br><br>	
		<textarea name="content" id="content" rows="10" cols="80" placeholder="내용입력">${info.content }</textarea>
		<br><br>	
		<c:forEach var="pInfo" items="${pInfo}" varStatus="loop">
			<input type="hidden" id="participant_seq_${loop.index}" value="${pInfo.participant_seq}"/>
			<input type="hidden" id="signerid_${loop.index}" value="${pInfo.signerid}"/>
			<input type="hidden" id="approvaltype_${loop.index}" value="${pInfo.approvaltype}"/>
		</c:forEach>
	</div>
	<div>
		<label for="file" class="btn-upload">파일 업로드</label>			
		<input type="file" id="file" name="uploadFile" class="uploadFile" 
		multiple="multiple" style="display:none;"/>
	</div>
	<c:forEach var="attach" items="${attach}">
	<input type="hidden" id="path" value="${attach.uploadPath}"/>
	<div class="uploadFileList">
		<ul>
			<li data-uuid="${attach.uuid}" data-filename="${attach.fileName}" data-path="${attach.uploadPath}">
			<c:if test="${info.attachcnt >0}">
				<span class="files"  data-type="file">${attach.fileName}</span>
				<button class="delete" onClick="ApprDocDeleteFiles()">❌</button>		
			</c:if>
			</li>
		</ul>
	</div>
	</c:forEach>
	<br>
	<div class="uploadResult"> 
		<ul></ul>
	</div>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${path}/resources/js/ReceiveFlowInfo_.js"></script>
<script src="<c:url value='/resources/js/InfoUploadFile.js'/>"></script>
<script>
var appr_seq = '<c:out value="${info.appr_seq}"/>';
var drafterid = '<c:out value="${info.drafterid}"/>';
var status = '<c:out value="${info.status}"/>';

window.addEventListener('keydown',function(e){
	if( (e.ctrlKey == true || e.metaKey == true && (e.keyCode == 78 || e.keyCode == 82)) || (e.keyCode == 116)){
		e.preventDefault();
		e.stopPropagation();
	}	
});
function pop() {
	window.open("${path}/dept/flowUseInfo","pop","width=768, height=400");
}
function Resubmission(){
	var loading = document.getElementById('loading')
	loading.style.display = 'flex';
	var formData = new FormData(); 
	var inputFile = $("input[name='uploadFile']");
	var files = inputFile[0].files;

	 if(files.length > 0){
		    formData.append('appr_seq', appr_seq);
		    formData.append('drafterid', $('#drafterid').val());
		    formData.append('status', status);
		    formData.append('folderid', $('#folderid').val());
		    formData.append('foldername', $('#foldername').val());
		    formData.append('bizunitcd', $('#bizunitcd').val());
		    formData.append('attachcnt', attachcnt);

		    // 파일 정보 추가
		    UploadFileAppend(formData);
		    $.ajax({
				type: 'post',
				url: '<c:url value="/approval/Resubmission"/>',
				data : formData,        
		        processData: false,
		        contentType: false,
				success: function(){
					setTimeout(function(){ 
						alert('해당 문서 재기안 되었습니다.');
						window.close();
						window.opener.location.reload();
						ResubmissionFlowStatusUpd();
						ResubmissionParticipantWrite();
					},3000)
				},
				error: function(error){
					alert('재기안 실패 하였습니다.');
					console.log(error);
				}
			})
	 }else{
	var param = {
			appr_seq : appr_seq,
			drafterid: drafterid, 
			status: status,
			folderid: $('#folderid').val(),
			foldername: $('#foldername').val(),
			bizunitcd: $('#bizunitcd').val(),
			attacnt: $('#attacnt').val()
			}
	$.ajax({
		type: 'post',
		url: '<c:url value="/approval/Resubmission"/>',
		data: param,
		success: function(){
			setTimeout(function(){ 
				alert('해당 문서 재기안 되었습니다.');
				window.close();
				window.opener.location.reload();
				ResubmissionFlowStatusUpd();
				ResubmissionParticipantWrite();
			},3000)
		},
		error: function(error){
			alert('재기안 실패 하였습니다.');
			console.log(error);
		}
	})
}

function ResubmissionFlowStatusUpd(){
	var flowSigner = [];	
	$('input[id^="participant_seq_"]').each(function(){
		var index = $(this).attr('id').split("_")[1];
		var participant_seq = $(this).val();
		var signerid = $(this).next().val();
		var approvaltype = $(this).next().next().val();
		
		flowSigner.push({
			appr_seq : appr_seq,
			participant_seq : participant_seq,
			signerid : signerid,
			approvaltype: approvaltype
		});		
	})
	
	$.ajax({
		type: 'post',
		url: '<c:url value="/participant/ResubmissionFlowStatusUpd"/>',
		data: JSON.stringify(flowSigner),
		contentType: 'application/json',
		success: function (response){
			console.log(response);
			console.log(JSON.stringify(flowSigner));
		},
		error: function(error){
			console.log(error);
		}
	})
}
}

function ResubmissionParticipantWrite() {
	var line_seq = $('#line_seq').val();
	//결재선 데이터
    var paticipant = [];

    $('#inputs .user-container').each(function () {
        var userContainer = $(this);
        var deptid = userContainer.find('input[name^="deptid_"]').val();
        var deptname = userContainer.find('input[name^="deptname_"]').val();
        var signername = userContainer.find('input[name^="signername_"]').val();
        var signerid = userContainer.find('input[name^="signerid_"]').val();
        var pos = userContainer.find('input[name^="pos_"]').val();
        var status = userContainer.find('select[name^="status_"]').val();

        paticipant.push({
        	deptid: deptid,
        	deptname: deptname,
        	signername: signername,
            signerid: signerid,
            pos: pos,
            status: status,
            appr_seq : appr_seq
        });
    });
    $.ajax({
        type: 'post',
        url: '<c:url value="/participant/ResubmissionParticipantWrite"/>',
        data: JSON.stringify(paticipant),
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
        },
        error: function (xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
}
</script>
</body>
</html>