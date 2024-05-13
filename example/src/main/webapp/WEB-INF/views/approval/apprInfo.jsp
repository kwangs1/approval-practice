<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
</head>
<link rel="stylesheet" href="${path}/resources/css/info.css">
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
<body onload="getAttachList()">
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>결재 중..</p>
</div>

<div class="post-container">
  <!-- 문서 본문영역 -->
  <h1 class="post-title">${info.title}</h1>
  <p class="post-info">기안자: ${info.draftername} | 작성일: ${info.regdate }</p>
  <p class="post-info">신청날짜: ${info.startdate} ~ ${info.enddate}</p>
  <div class="post-content">
    <p>${info.content }</p>
  </div>
  <br>
  	<div>
		<c:choose>
			<c:when test="${info.status != 256 && pInfo.signerid eq user.id}">
			<div>
				<label for="ModifyForm" class="btn-upload">업로드 파일수정</label>
				<input type="button" id="ModifyForm" onClick="AttachModifyForm()" style="display:none;"/>
			</div>
			</c:when>
			<c:when test="${ (info.status == 256 && info.attachcnt > 0) || (info.status != 256 && info.attachcnt > 0)}">
				<span>첨부파일</span>
			</c:when>
		</c:choose>
		
		<div class="InfoUploadResult">
			<ul>
			</ul>
		</div>
	</div>
	<!-- 결재선 영역 -->
	<div class="FlowContainer">
		<c:forEach var="pList" items="${pList}"> 
			<input type="hidden" id="participant_seq" value="${pList.participant_seq}" />
			<input type="hidden" id="signerid" value="${pList.signerid}" />
			<input type="hidden" id="approvaltype" value="${pList.approvaltype}" />
			<div class="FlowChart">
				<span class="position" id="pos">${pList.pos}</span>
				<input type="text" class="signername" id="signername" value="${pList.signername}" disabled/>		
			</div>
		</c:forEach>
	</div>
	<br>
  <c:if test="${info.status == 1 && pInfo.approvaltype == 4}">
  	<button onclick="FlowAppr()" class="button" id="btn">결재</button>  
  </c:if>
  <button onclick="window.close()" class="button">닫기</button>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/InfoUploadFile_.js'/>"></script>
<script>
var appr_seq = '<c:out value="${info.appr_seq}"/>';
var participant_seq = '<c:out value="${pInfo.participant_seq}"/>';
var approvaltype = '<c:out value="${pInfo.approvaltype}"/>';
var approvalstatus = '<c:out value="${pInfo.approvalstatus}"/>';
var signerid = '<c:out value="${userId}"/>';
var drafterid = '<c:out value="${info.drafterid}"/>';
var regdate = '<c:out value="${info.regdate}"/>';

var param = {	
	appr_seq : appr_seq,
	participant_seq : participant_seq,
	approvaltype : approvaltype,
	approvalstatus : approvalstatus,
	signerid : signerid}

function FlowAppr(){				
	var loading = document.getElementById('loading')
	loading.style.display = 'flex';
	$.ajax({
		type: 'post',
		url: '<c:url value="/participant/FlowAppr"/>',
		data: param,
		success: function(response){		
			setTimeout(function(){
				alert("결재가 완료되었습니다.");
				window.close();
				opener.location.reload();	
			},3000)
		},
		error: function(xhr,status,error){
			alert("결재 실패");
			console.log(xhr);
			console.log(status);
		}
	});
}
//첨부파일 수정폼
function AttachModifyForm(){
	url="<c:url value='/AttachModifyForm'/>"+"?appr_seq="+appr_seq;
	window.open(url,"attachModify",'width=680px, height=300px');
}
</script>
</body>
</html>