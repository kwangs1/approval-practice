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
input[type="text"]{height:30px; border:1px solid #ccc; border-radius:5px; padding: 5px; font-size: 14px; pointer-events:none;}
.sender_info{position: absolute; z-index:2; font-weight:bold}
.stampImage{position: absolute; z-index:1; left:430px; top:-30px; opacity: 0.7;}
.getStampImage{position: absolute; z-index:1; left:430px; top:-30px; opacity: 0.7;}
.receiveStamp{display:flex; justify-content:center; position: relative;}
</style>
<body onload="init()">
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p id="text"></p>
</div>


<input type="hidden" name="stampname" id="stampname" />
<div class="post-container">
	<c:if test="${info.status == 256 && info.docattr eq '1' && info.poststatus eq '1' }">
	  <button onclick="getFlowDeptStamp()" class="button">관인서명</button> 
	</c:if>
	<c:if test="${SendInfo.recdocstatus eq '2' && SendInfo.receiverid eq user.deptid}">
		<button onclick="DocOpinion()" class="button" >반송</button>
	</c:if>
  <!-- 문서 본문영역 -->
  <h1 class="post-title">${info.title}</h1>
  <c:if test="${info.docattr eq '1'}">
  	<div class="receivers_info"></div>
  </c:if>
  <p class="post-info">기안자: ${info.draftername} | 작성일: ${info.regdate }</p>
  <p class="post-info">신청날짜: ${info.startdate} ~ ${info.enddate}</p>
  <div class="post-content">
    <p>${info.content }</p>
  </div>
    <br><br>
  <c:if test="${info.docattr eq '1'}">
	  <div class="receiveStamp">
		<div class="sender_info"></div>	
		<div class="stampImage"></div><!-- 발송 시 지정하는 관인이미지 보여주는곳 -->
		<div class="getStampImage"></div><!-- 발송 이후 문서함& 접수대기 에서 관인보여주기 -->
	  </div>
  </c:if>
  <br>
  	<div>
		<c:choose>
			<c:when test="${info.status != 256 && pInfo.signerid eq user.id && info.draftsrctype != '1'}">
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
		<c:if test="${info.draftsrctype ne '1' }">
			<c:forEach var="pList" items="${pList}"> 
				<input type="hidden" id="participant_seq" value="${pList.participant_seq}" />
				<input type="hidden" id="signerid" value="${pList.signerid}" />
				<input type="hidden" id="approvaltype" value="${pList.approvaltype}" />
				<div class="FlowChart">
					<span class="position" id="pos">${pList.pos}</span>
					<input type="text" class="signername" id="signername" value="${pList.signername}" disabled/>		
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${info.draftsrctype eq '1' }">
			<c:forEach var="pList" items="${DraftflowList}"> 
				<input type="hidden" id="participant_seq" value="${pList.participant_seq}" />
				<input type="hidden" id="signerid" value="${pList.signerid}" />
				<input type="hidden" id="approvaltype" value="${pList.approvaltype}" />
				<div class="FlowChart">
					<span class="position" id="pos">${pList.pos}</span>
					<input type="text" class="signername" id="signername" value="${pList.signername}" disabled/>		
				</div>
			</c:forEach>
		</c:if>
	</div>
	<br>
  <c:if test="${info.status == 1 && pInfo.approvaltype == 4}">
  	<button onclick="FlowAppr()" class="button" id="btn">결재</button>  
  </c:if>
  <c:if test="${info.status == 256 && info.docattr eq '1' && info.poststatus eq '1' }">
  	<button onclick="DocSend()" class="button" id="btn">발송</button> 
  </c:if>
  <c:if test="${info.status == 256 && SendInfo.receiverid eq deptId}">
		<button onclick="RceptForm()" class="button" >접수</button>
  </c:if>
  <button onclick="window.close()" class="button">닫기</button>
</div>

<input type="hidden" id="parentOpi"/>
<input type="hidden" id="credate"/>


<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/InfoUploadFile_.js'/>"></script>
<script src="<c:url value='/resources/js/DocCookie.js'/>"></script>
<script>
var appr_seq = '<c:out value="${info.appr_seq}"/>';
var participant_seq = '<c:out value="${pInfo.participant_seq}"/>';
var approvaltype = '<c:out value="${pInfo.approvaltype}"/>';
var approvalstatus = '<c:out value="${pInfo.approvalstatus}"/>';
var signerid = '<c:out value="${userId}"/>';
var drafterid = '<c:out value="${info.drafterid}"/>';
var regdate = '<c:out value="${info.regdate}"/>';
var draftsrctype = '<c:out value="${info.draftsrctype}"/>';
var RECEIPTAPPR_SEQ = '<c:out value="${ReceptInfo.appr_seq}"/>';
var sendername = '<c:out value="${info.sendername}"/>';
var receivers = '<c:out value="${info.receivers}"/>';
var docattr = '<c:out value="${info.docattr}"/>';
var sendid = '<c:out value="${info.sendid}"/>';
var poststatus = '<c:out value="${info.poststatus}"/>';
var childSendid = '<c:out value="${SendInfo.sendid}"/>';
var OpinionCheck = '<c:out value ="${OpinionCheck}"/>';
var sendtype = '<c:out value="${SendInfo.sendtype}"/>';
var recdocstatus = '<c:out value="${SendInfo.recdocstatus}"/>';
var CheckOpinion = '<c:out value="false"/>';

var param = {	
	appr_seq : appr_seq,
	participant_seq : participant_seq,
	approvaltype : approvaltype,
	approvalstatus : approvalstatus,
	signerid : signerid}

function FlowAppr(){				
	var loading = document.getElementById('loading');
	var p  = document.getElementById('text');
	p.innerHTML = '결재 중...';
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

function DocSend(){
	var loading = document.getElementById('loading');
	var p  = document.getElementById('text');
	p.innerHTML = '발송 중...';
	loading.style.display = 'flex';	
	$.ajax({
		type:'post',
		url: '<c:url value="/approval/DocSend"/>',
		data: {appr_seq: appr_seq,stampname:$('#stampname').val()},
		success: function(){
			setTimeout(function(){
				alert("발송처리 되었습니다.");
				window.close();
				window.opener.location.reload();
			},3000)
		},
		error: function(xhr, status){
			alert("발송처리 도중 오류가 발생하였습니다.");
			console.log(xhr);
			console.log(status);
		}
	});
}
//접수 폼
function RceptForm(){
	url = '<c:url value="/approval/RceptDocForm"/>'+'?appr_seq='+appr_seq;
	window.location.href = url;
}
//첨부파일 부분 접수문서와 기안문서가 출력되는부분 다르게
function init(){
	$.ajax({
		url: '<c:url value="/stamp/getApprStampInfo"/>',
		type: 'get',
		data: {appr_seq :appr_seq},
		success: function(response){
			var localName = response.id+"."+response.name;
			var fileCallPath = encodeURIComponent("s_" + localName);
			$('.getStampImage').html("<img src='/kwangs/display?name="+fileCallPath+"'>");
		},
		error: function(xhr, status, error){
			console.log(error)
		}
	})
	
	if(draftsrctype != 1){
		getAttachList();
	}else{
		getRceptAttachList();
	}
	
	if(docattr === '1'){
		$('.sender_info').html('발신처: '+sendername);
		$('.receivers_info').html('수신처: '+receivers);
	}

	if(docattr === '1' && '<c:out value="${info.draftsrctype}"/>' === '1'){
		$('.sender_info').html('발신처: '+ '<c:out value="${OrgDocReceivers.sendername}"/>');
		$('.receivers_info').html('수신처: '+ '<c:out value="${OrgDocReceivers.receivers}"/>');		
	}
}

function getFlowDeptStamp(){
	url= '<c:url value="/stamp/getFlowDeptStampList"/>'
	url += '?appr_seq='+appr_seq
	window.open(url,'stamp','width=340px, height=280px');
}

window.addEventListener('message',function(e){
	e.preventDefault();
	var data = e.data;
	
	if(data && typeof data === 'object'){
		var id = data.id;
		var name = data.name;
		
		$('#stampname').val(name);
		
		var fileCallPath = encodeURIComponent("s_" + id+"."+name);
		var stampImage = $('.stampImage');
		stampImage.empty();
		stampImage.append("<img src='/kwangs/display?name="+fileCallPath+"'>");		
	}
})

//반송
function RceptDocReturn(){
	var loading = document.getElementById('loading');
	var p  = document.getElementById('text');
	p.innerHTML = '반송 중...';
	loading.style.display = 'flex';
	
	var param = {
			appr_seq: appr_seq,
			opinioncontent: $('#parentOpi').val(),
			credate: $('#credate').val()
	}
	$.ajax({
		url: '<c:url value="/approval/RecptDocReturn"/>',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(param),
		success: function(){
			loading.style.display = 'none';
			alert("반송처리가 완료되었습니다.");
			window.close();
			window.opener.location.reload();
		},
		error: function(xhr, status, error){
			alert("반송실패 ㅋ"+error);
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	})
}
function DocOpinion(){
	url = '<c:url value="/approval/DocOpinionForm"/>';
	var childWindow =window.open(url,'DocOpinion','width=750px, height=120px');
}

window.addEventListener('beforeunload',function(){
	deleteCookie('opinion');
})
</script>
</body>
</html>