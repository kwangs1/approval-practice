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
</style>
<body>
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>결재 중..</p>
</div>
<div class="post-container">
  <h1 class="post-title">${info.title}</h1>
  <p class="post-info">기안자: ${info.draftername} | 작성일: ${info.regdate }</p>
  <p class="post-info">신청날짜: ${info.startdate} ~ ${info.enddate}</p>
  <div class="post-content">
    <p>${info.content }</p>
  </div>
  
  	<div>
		<c:choose>
			<c:when test="${info.status != 256}">
			<div>
				<label for="file" class="btn-upload">파일 업로드</label>
	
				<a href="javascript:AttachModifyForm()">수정</a>
				<input type="file" id="file" name="uploadFile" class="uploadFile" 
				multiple="multiple" style="display:none;"/>
			</div>
			</c:when>
			<c:when test="${info.status == 256 }">
				<span>- 첨부파일 -</span>
			</c:when>
		</c:choose>
		
		<div class="uploadResult">
			<ul>
			</ul>
		</div>
	</div>
	<br>
  <c:if test="${info.status == 1 && pInfo.approvaltype == 4}">
  	<button onclick="FlowAppr()" class="button" id="btn">결재</button>  
  </c:if>
  <button onclick="window.close()" class="button">닫기</button>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var appr_seq = '<c:out value="${info.appr_seq}"/>';
var participant_seq = '<c:out value="${pInfo.participant_seq}"/>';
var approvaltype = '<c:out value="${pInfo.approvaltype}"/>';
var approvalstatus = '<c:out value="${pInfo.approvalstatus}"/>';
var signerid = '<c:out value="${userId}"/>';
var drafterid = '<c:out value="${info.drafterid}"/>';

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

//해당 문서에 대한 업로드한 첨부파일 리스트
$.getJSON('<c:url value="/getAttachList"/>',{appr_seq: appr_seq}, function(arr){
	var str = "";
	
	$(arr).each(function(i,attach){
		str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>";
		str += "<div><span style='cursor: pointer;'>" + attach.fileName + "</span>";
		str += "</div></li><br>";
	})
	$('.uploadResult').html(str);
});
//첨부파일 다운로드
$('.uploadResult').on('click','li',function(e){
	var liObj = $(this);
	var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
	self.location='<c:url value="/download"/>'+'?id='+drafterid+'&fileName='+path;
});
//첨부파일 수정폼
function AttachModifyForm(){
	url="<c:url value='/AttachModifyForm'/>"+"?appr_seq="+appr_seq;
	window.open(url,"attachModify",'width=680px, height=300px');
}
</script>
</body>
</html>