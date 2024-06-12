<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<link rel="stylesheet" href="<c:url value='/resources/css/info.css'/>"/>
<style>
.btn-upload {
width: 150px; height: 30px; background: #fff; border: 1px solid rgb(77,77,77); border-radius: 10px;
font-weight: 500; cursor: pointer; display: flex; align-items: center; justify-content: center;
&:hover {background: rgb(77,77,77); color: #fff; }
}
li{list-style:none; padding-left:0px;}
.signername{height: 30px; border: 1px solid #ccc; border-radius: 5px; padding: 5px; font-size: 14px;}
.position {font-weight: bold; font-size: 12px;}
.FlowChart{display: flex; flex-direction: column; margin-right: 10px;}
.FlowContainer{display: flex;flex-wrap: wrap;}
</style>
<body onload="init()">
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>접수 중..</p>
</div>
	
<div class="post-container">
<%@ include file="../participant/ParticipantWrite.jsp" %>
  <!-- 문서 본문영역 -->
  <h1 class="post-title">${info.title}</h1>
  <p class="post-info">기안자: ${info.draftername} | 작성일: ${info.regdate }</p>
  <p class="post-info">신청날짜: ${info.startdate} ~ ${info.enddate}</p>
  <div class="post-content">
    <p>${info.content }</p>
  </div>
  <br>
  
  	<div>

  	<c:if test="${info.status == 256 && info.attachcnt >0}">
  		<span>첨부파일</span>
  	</c:if>

		<br>
		<div class="InfoUploadResult">
			<ul></ul>
		</div>
	</div>
	<div class="FlowContainer">
	<c:forEach var="pList" items="${flowList}"> 
		<input type="hidden" id="participant_seq" value="${pList.participant_seq}" />
		<input type="hidden" id="signerid" value="${pList.signerid}" />
		<input type="hidden" id="approvaltype" value="${pList.approvaltype}" />
		<div class="FlowChart">
				<span class="position" id="pos">${pList.pos}</span>
				<input type="text" class="signername" id="signername" value="${pList.signername}" disabled/>		
		</div>
	</c:forEach>
	</div>
	<br><br>
</div>

	<input type="hidden" name="drafterid" id="drafterid" />
	<input type="hidden" name="draftername" id="draftername" />
	<input type="hidden" name="drafterdeptid" id="drafterdeptid"/>
	<input type="hidden" name="drafterdeptname" id="drafterdeptname"/>
	<input type="hidden" name="docregno"/>
	<input type="hidden" name="folderid" id="folderid"/>
	<input type="hidden" name="bizunitcd" id="bizunitcd"/>
	<input type="hidden" name="foldername" id="foldername"/>
	<input type="hidden" name="docattr" id="docattr"/>
	<input type="hidden" name="draftsrctype" id="draftsrctype"/>
	
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/InfoUploadFile_.js'/>"></script>
<script>
var SendID = '<c:out value="${sendInfo.sendid}"/>';
var title = '<c:out value="${info.title}"/>';
var content = '<c:out value="${info.content}"/>';
var startdate = '<c:out value="${info.startdate}"/>';
var enddate = '<c:out value="${info.enddate}"/>';
var attachcnt = '<c:out value="${info.attachcnt}"/>';
var drafterid = '<c:out value="${userId}" />';
var draftername = '<c:out value="${user.name}" />';
var drafterdeptid = '<c:out value="${deptId}" />';
var drafterdeptname = '<c:out value="${user.deptname}" />';
var docregno = '<c:out value="${res.abbreviation}"/>';
var DocAttr = '<c:out value="${info.docattr}"/>';
var appr_seq = '<c:out value="${info.appr_seq}"/>';

function init(){
	getAttachList();
	flowPop();
}
function flowPop(){
	url = "<c:url value='/dept/RceptflowUseInfo'/>" + "?appr_seq=" + appr_seq;
	window.open(url,'flow','width=768px, height=550px');
}

window.addEventListener('message', function(e){
	var data = e.data;
	var selectedApFolder = data.selectedApFolder;
	
	$('#folderid').val(selectedApFolder.fldrid);
	$('#bizunitcd').val(selectedApFolder.bizunitcd);
	$('#foldername').val(selectedApFolder.fldrname);
});

function RceptDocSanc(){
	var loading = document.getElementById('loading')
	loading.style.display = 'flex';
	
	var paramData = {
			sendid: SendID,
			title: title,
			content: content,
			startdate: startdate,
			enddate: enddate,
			attachcnt: attachcnt,
			drafterid: drafterid,
			draftername: draftername,
			drafterdeptid: drafterdeptid,
			drafterdeptname: drafterdeptname,
			orgdraftdeptid: drafterdeptid,
			docregno: docregno,
			folderid: $('#folderid').val(),
			bizunitcd: $('#bizunitcd').val(),
			foldername: $('#foldername').val(),
			docattr: DocAttr,
			draftsrctype: $('#draftsrctype').val()
	}
	
	$.ajax({
		type: 'post',
		url: '<c:url value="/approval/RceptDocSang"/>',
		data: paramData,
		success: function(response){
			console.log(response);
			participant();
		},
		error: function(xhr,status){
			console.log(xhr);
			console.log(status);
		}
	})
}
</script>
</body>
</html>